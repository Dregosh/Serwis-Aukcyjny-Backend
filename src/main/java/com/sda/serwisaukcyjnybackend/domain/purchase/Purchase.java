package com.sda.serwisaukcyjnybackend.domain.purchase;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.purchase.event.PurchaseCreated;
import com.sda.serwisaukcyjnybackend.domain.rating.Rating;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @NotNull
    private BigDecimal price;

    @OneToOne(mappedBy = "purchase")
    private Rating rating;

    @Column(name = "is_buy_now")
    @NotNull
    private Boolean isBuyNow;

    public Purchase(Auction auction, Bid maxBid, Boolean isBuyNow) {
        this.buyer = maxBid.getUser();
        this.auction = auction;
        this.price = maxBid.getBidPrice();
        this.isBuyNow = isBuyNow;
    }

    public Purchase(User user, Auction auction, BigDecimal price, Boolean isBuyNow) {
        this.buyer = user;
        this.auction = auction;
        this.price = price;
        this.isBuyNow = isBuyNow;
    }

    public Long getBuyerId() {
        return this.getBuyer().getId();
    }
}
