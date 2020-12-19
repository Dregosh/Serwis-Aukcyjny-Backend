package com.sda.serwisaukcyjnybackend.domain.auction.event;

import lombok.Value;

@Value
public class AuctionEndedWithoutPurchase {
    String email;
    Long id;
    String title;
}
