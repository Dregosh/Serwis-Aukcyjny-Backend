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
    private Auction auction;

    @ManyToOne
    private User user;

    @NotNull
    @Min(0)
    @Column(name = "bid_price")
    private BigDecimal bidPrice;
}
