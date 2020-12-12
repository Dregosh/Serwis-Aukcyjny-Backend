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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "auction_sequence", allocationSize = 1)
    Long id;

    // placeholder until Category is created
    String category;

    String title;
    String description;
    BigDecimal minPrice;
    BigDecimal buyNowPrice;
    Boolean isPromoted;
    String location;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer viewNumber;

}
