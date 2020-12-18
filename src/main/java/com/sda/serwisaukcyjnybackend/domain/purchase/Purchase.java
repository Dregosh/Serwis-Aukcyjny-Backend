package com.sda.serwisaukcyjnybackend.domain.purchase;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.purchase.event.BuyNowPurchase;
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
public class Purchase extends AbstractAggregateRoot<Purchase> {

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

    public Purchase(Auction auction, Bid maxBid) {
        this.buyer = maxBid.getUser();
        this.auction = auction;
        this.price = maxBid.getBidPrice();
    }

    @PostPersist
    private void informAboutBuyNowPurchase() {
        if (this.getIsBuyNow()) {
            registerEvent(new BuyNowPurchase(
                    id,
                    buyer.getEmail(),
                    auction.getSeller().getDisplayName(),
                    auction.getId(),
                    auction.getTitle(),
                    price
                    ));
        }
    }
}
