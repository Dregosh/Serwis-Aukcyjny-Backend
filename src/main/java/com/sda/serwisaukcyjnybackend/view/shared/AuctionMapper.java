package com.sda.serwisaukcyjnybackend.view.shared;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;

public class AuctionMapper {

    public static SimpleAuctionDTO mapToSimpleAuction(Auction auction) {
        return SimpleAuctionDTO.builder()
                               .id(auction.getId())
                               .title(auction.getTitle())
                               .bidPrice(auction.getMaxBid())
                               .buyNowPrice(auction.getBuyNowPrice())
                               .alreadyBidded(auction.getStatus() == AuctionStatus.STARTED)
                               .build();
    }
}
