package com.sda.serwisaukcyjnybackend.view.auction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AuctionSort {
    ID_ASC("id", "asc"),
    ID_DESC("id", "desc"),
    BID_PRICE_ASC("maxBid", "asc"),
    BID_PRICE_DESC("maxBid", "desc"),
    BUY_NOW_PRICE_ASC("buyNowPrice", "asc"),
    BUY_NOW_PRICE_DESC("buyNowPrice", "desc"),
    TITLE_ASC("title", "asc"),
    TITLE_DESC("title", "desc");

    String field;
    String sortType;
}
