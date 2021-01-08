package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class UserCannotEndNotOwnedAuctionException extends RuntimeException {
    public UserCannotEndNotOwnedAuctionException(Long auctionId, String cause) {
        super(String.format("Cannot end auction %d - %s", auctionId, cause));
    }
}
