package com.sda.serwisaukcyjnybackend.domain.auction;

import com.sda.serwisaukcyjnybackend.config.app.converters.AddressConverter;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
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

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    private List<Bid> bids = new ArrayList<>();

    @OneToOne(mappedBy = "auction")
    private Purchase purchase;

    @OneToMany(mappedBy = "auction")
    private List<Observation> observations = new ArrayList<>();

    public BigDecimal getMaxBid() {
        //TODO
        /*return bids.stream()
                   .map(Bid::getBidPrice)
                   .max(BigDecimal::compareTo)
                   .orElse(minPrice);*/

        //currently using mock for quick db testing purpose
        return BigDecimal.ONE;
    }
}
