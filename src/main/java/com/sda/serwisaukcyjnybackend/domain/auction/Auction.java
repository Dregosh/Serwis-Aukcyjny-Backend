package com.sda.serwisaukcyjnybackend.domain.auction;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    Long id;

    @Version
    Long version;

    @NotNull
    String title;

    @NotNull
    String description;

    @NotNull
    @Min(0)
    BigDecimal minPrice;

    @NotNull
    @Min(0)
    BigDecimal buyNowPrice;

    @NotNull
    Boolean isPromoted;

    @NotNull
    String location;

    @NotNull
    LocalDateTime startDateTime;

    @NotNull
    LocalDateTime endDateTime;

    @NotNull
    Status status;

}
