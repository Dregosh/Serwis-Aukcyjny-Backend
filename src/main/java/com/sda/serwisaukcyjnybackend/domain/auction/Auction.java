package com.sda.serwisaukcyjnybackend.domain.auction;

import com.sda.serwisaukcyjnybackend.config.app.converters.AddressConverter;
import com.sda.serwisaukcyjnybackend.domain.auction.event.AuctionCreated;
import com.sda.serwisaukcyjnybackend.domain.auction.event.AuctionEndedWithoutPurchase;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.category.Category;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraph(name = "auction-photos", attributeNodes = {
        @NamedAttributeNode("photos")
})
@NamedEntityGraph(name = "auction-photos-seller", attributeNodes = {
        @NamedAttributeNode("photos"),
        @NamedAttributeNode("seller")
})
public class Auction extends AbstractAggregateRoot<Auction> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Column(name = "min_price")
    @Min(0)
    private BigDecimal minPrice;

    @Column(name = "buy_now_price")
    @Min(0)
    private BigDecimal buyNowPrice;

    @Column(name = "is_promoted")
    @NotNull
    private Boolean isPromoted;

    @Convert(converter = AddressConverter.class)
    private Address location;

    @Column(name = "start_date_time")
    @NotNull
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    @NotNull
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AuctionStatus status;

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    private List<Bid> bids = new ArrayList<>();

    @OneToOne(mappedBy = "auction")
    private Purchase purchase;

    @OneToMany(mappedBy = "auction")
    private List<Observation> observations = new ArrayList<>();

    @Column(name = "max_bid")
    private BigDecimal maxBid;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    private List<Photo> photos = new ArrayList<>();

    public Auction(@NotNull User seller, @NotNull String title,
                   @NotNull String description, @NotNull @Min(0) BigDecimal minPrice,
                   @Min(0) BigDecimal buyNowPrice, @NotNull Boolean isPromoted,
                   @NotNull LocalDateTime startDateTime, @NotNull LocalDateTime endDateTime,
                   @NotNull Category category) {
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.minPrice = minPrice;
        this.buyNowPrice = buyNowPrice;
        this.isPromoted = isPromoted;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = seller.getAddress();
        this.category = category;
        this.version = 0L;
        this.status = AuctionStatus.CREATED;
    }

    @PostPersist
    private void informAboutEvent() {

        if(this.getStatus() == AuctionStatus.CREATED) {
            informAboutCreatedAuction();
        }

        if(this.getStatus() == AuctionStatus.ENDED && this.bids.isEmpty()) {
            informAboutEndedAuctionWithoutPurchase();
        }
    }

    private void informAboutCreatedAuction() {
        registerEvent(new AuctionCreated(seller.getEmail(), id, title, minPrice, buyNowPrice,
                startDateTime, endDateTime));
    }

    private void informAboutEndedAuctionWithoutPurchase() {
        registerEvent(new AuctionEndedWithoutPurchase(seller.getEmail(), id, title));
    }

    public BigDecimal getMaxBid() {
        return maxBid != null ? maxBid : minPrice;
    }

    public boolean canBeBidded() {
        return !isBought() && status == AuctionStatus.STARTED;
    }

    public boolean isBought() {
        return purchase != null && status == AuctionStatus.ENDED;
    }

    public void markAsEnded() {
        status = AuctionStatus.ENDED;
    }

    public boolean canBeBoughtNow() {
        return status == AuctionStatus.CREATED && purchase == null;
    }

    public Long getSellerId() {
        return seller.getId();
    }

    public void setStarted() {
        status = AuctionStatus.STARTED;
        maxBid = minPrice != null ? minPrice : BigDecimal.ZERO;
    }

    public String getSellerDisplayName() {
        return seller.getDisplayName();
    }
}
