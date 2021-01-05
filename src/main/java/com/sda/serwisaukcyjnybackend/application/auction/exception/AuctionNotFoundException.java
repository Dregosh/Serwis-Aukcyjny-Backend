package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class AuctionNotFoundException extends RuntimeException {
    public AuctionNotFoundException(Long auctionId, Long sellerId) {
        super(String.format("Could not find auction by id: %d and sellerId: %d", auctionId, sellerId));
    }
}
