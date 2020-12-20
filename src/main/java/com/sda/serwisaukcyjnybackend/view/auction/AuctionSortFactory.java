package com.sda.serwisaukcyjnybackend.view.auction;

import org.springframework.data.domain.Sort;

public class AuctionSortFactory {
    private static final String IS_PROMOTED = "isPromoted";

    public static Sort createSort() {
        return Sort.by(IS_PROMOTED).descending();
    }

    public static Sort createSort(AuctionSort auctionSort) {
        return Sort.by(IS_PROMOTED).descending().and(Sort.by(Sort.Direction.fromString(auctionSort.sortType),
                auctionSort.field));
    }
}
