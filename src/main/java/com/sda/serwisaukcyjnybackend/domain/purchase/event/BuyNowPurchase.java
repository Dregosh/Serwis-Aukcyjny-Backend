package com.sda.serwisaukcyjnybackend.domain.purchase.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class BuyNowPurchase {
    Long purchaseId;
    String sellerEmail;
    String buyerEmail;
    String sellerName;
    String BuyerName;
    Long auctionId;
    String auctionTitle;
    BigDecimal price;
}
