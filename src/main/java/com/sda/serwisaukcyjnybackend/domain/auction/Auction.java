package com.sda.serwisaukcyjnybackend.domain.auction;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Auction {

    //TODO add fields:
    // - Image
    // - Category

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "auction_sequence", allocationSize = 1)
    Long id;

    String title;
    String description;
    BigDecimal minPrice;
    BigDecimal buyNowPrice;
    Boolean isPromoted;
    String location;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    Integer viewedCount;

}
