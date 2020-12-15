package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class CannotBuyNowException extends RuntimeException {
    public CannotBuyNowException(Long auctionId) {
        super(String.format("Cannot buy now auction %d", auctionId));
    }
}
