package com.sda.serwisaukcyjnybackend.domain.bid;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Min(0)
    @Column(name = "bid_price")
    private BigDecimal bidPrice;

    public Bid(Auction auction, User user,
               @NotNull @Min(0) BigDecimal bidPrice) {
        this.auction = auction;
        this.user = user;
        this.bidPrice = bidPrice;
    }
}
