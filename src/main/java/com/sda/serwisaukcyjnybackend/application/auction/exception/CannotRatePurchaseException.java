package com.sda.serwisaukcyjnybackend.application.auction.exception;

public class CannotRatePurchaseException extends RuntimeException {
    public CannotRatePurchaseException(Long purchaseId, String cause) {
        super(String.format("Cannot rate purchase %d - %s", purchaseId, cause));
    }
}
