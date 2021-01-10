package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class CannotObserveAuctionException extends RuntimeException {
    public CannotObserveAuctionException(Long auctionId) {
        super(String.format("Cannot observe auction %d", auctionId));
    }
}
