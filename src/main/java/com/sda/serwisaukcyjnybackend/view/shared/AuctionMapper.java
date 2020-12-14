package com.sda.serwisaukcyjnybackend.view.shared;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;

public class AuctionMapper {

    public static SimpleAuctionDTO mapToSimpleAuction(Auction auction) {

        //TODO
        //  mainPhotoName mapping (field not yet present in Auction Class)

        return SimpleAuctionDTO.builder()
                               .id(auction.getId())
                               .title(auction.getTitle())
                               .bidPrice(auction.getMaxBid())
                               .buyNowPrice(auction.getBuyNowPrice())
                               .alreadyBidded(!auction.getBids().isEmpty())
                               .build();
    }
}
