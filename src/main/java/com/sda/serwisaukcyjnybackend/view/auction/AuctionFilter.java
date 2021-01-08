package com.sda.serwisaukcyjnybackend.view.auction;

import java.util.Map;
import java.util.stream.Collectors;

public enum AuctionFilter {
    BUY_NOW_PRICE_FROM,
    BUY_NOW_PRICE_TO,
    BID_PRICE_FROM,
    BID_PRICE_TO,
    ONLY_PROMOTED,
    ONLY_BUY_NOW,
    ONLY_CAN_BID,
    ALL_STATUSES;

    public static Map<AuctionFilter, String> mapToFilterAuctionMap(Map<String, String> stringMap) {
        return stringMap.entrySet().stream()
                .filter(entry -> {
                    try {
                      AuctionFilter.valueOf(entry.getKey());
                      return true;
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .collect(Collectors.toMap(entry -> AuctionFilter.valueOf(entry.getKey()), Map.Entry::getValue));
    }
}
