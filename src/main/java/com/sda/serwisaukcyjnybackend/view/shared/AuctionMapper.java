package com.sda.serwisaukcyjnybackend.view.shared;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;

public class AuctionMapper {

    public static SimpleAuctionDTO mapToSimpleAuction(Auction auction) {
        return SimpleAuctionDTO.builder()
                .id(auction.getId())
                .bidPrice(auction.getMaxBid())
                .buyNowPrice(auction.getBuyNowPrice())
                .build();
    }
}
