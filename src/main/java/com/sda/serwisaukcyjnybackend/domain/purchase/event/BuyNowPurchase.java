package com.sda.serwisaukcyjnybackend.domain.purchase.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BuyNowPurchase {
    Long id;
    String buyerEmail;
    String sellerName;
    Long auctionId;
    String auctionTitle;
    BigDecimal price;
}
