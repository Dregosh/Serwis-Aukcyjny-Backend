package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class CannotObserveAuctionException extends RuntimeException {
    public CannotObserveAuctionException(Long auctionId, String cause) {
        super(String.format("Cannot observe auction %d - %s", auctionId, cause));
    }
}
