package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class CannotBidAuctionException extends RuntimeException {
    public CannotBidAuctionException(Long auctionId) {
        super(String.format("Cannot bid auction %d", auctionId));
    }
}
