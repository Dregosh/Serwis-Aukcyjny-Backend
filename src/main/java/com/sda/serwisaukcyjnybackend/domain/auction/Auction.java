package com.sda.serwisaukcyjnybackend.domain.auction;

import com.sda.serwisaukcyjnybackend.config.app.converters.AddressConverter;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
public class Auction {

    //TODO add fields:
    // - Image
    // - Category
    // - View Count

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
    @NotNull
    @Min(0)
    private BigDecimal minPrice;

    @Column(name = "buy_now_price")
    @NotNull
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

    //                                 TODO - fetchType resolve
    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER)
    private List<Bid> bids = new ArrayList<>();

    @OneToOne(mappedBy = "auction")
    private Purchase purchase;

    @OneToMany(mappedBy = "auction")
    private List<Observation> observations = new ArrayList<>();

    public BigDecimal getMaxBid() {
        return bids.stream()
                   .map(Bid::getBidPrice)
                   .max(BigDecimal::compareTo)
                   .orElse(minPrice);
    }

    public Auction(@NotNull User seller, @NotNull String title,
                   @NotNull String description, @NotNull @Min(0) BigDecimal minPrice,
                   @NotNull @Min(0) BigDecimal buyNowPrice, @NotNull Boolean isPromoted,
                   @NotNull LocalDateTime startDateTime, @NotNull LocalDateTime endDateTime) {
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.minPrice = minPrice;
        this.buyNowPrice = buyNowPrice;
        this.isPromoted = isPromoted;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = seller.getAddress();
        this.version = 0L;
        this.status = AuctionStatus.CREATED;
    }

}
