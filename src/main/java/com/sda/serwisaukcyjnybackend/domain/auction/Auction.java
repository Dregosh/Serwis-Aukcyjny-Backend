package com.sda.serwisaukcyjnybackend.domain.auction;

import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import lombok.*;

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
@Builder
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

    @NotNull
    private String location;

    @Column(name = "start_date_time")
    @NotNull
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    @NotNull
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @OneToMany(mappedBy = "auction")
    private List<Bid> bids = new ArrayList<>();
}
