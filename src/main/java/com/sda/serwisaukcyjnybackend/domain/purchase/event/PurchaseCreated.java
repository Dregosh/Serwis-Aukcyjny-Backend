package com.sda.serwisaukcyjnybackend.domain.purchase.event;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PurchaseCreated {
    Long purchaseId;
    String sellerEmail;
    String buyerEmail;
    String sellerName;
    String buyerName;
    Long auctionId;
    String auctionTitle;
    BigDecimal price;
    Boolean isBuyNow;
}
