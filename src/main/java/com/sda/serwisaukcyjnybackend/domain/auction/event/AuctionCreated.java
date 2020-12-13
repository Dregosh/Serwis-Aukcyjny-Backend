package com.sda.serwisaukcyjnybackend.domain.auction.event;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class AuctionCreated {
    String title;
    BigDecimal minPrice;
    BigDecimal buyNowPrice;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}
