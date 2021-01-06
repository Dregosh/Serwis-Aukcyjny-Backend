package com.sda.serwisaukcyjnybackend.application.auth.exception;

public class PremiumOrderNotFoundException extends RuntimeException {
    public PremiumOrderNotFoundException(String orderId) {
        super(String.format("Could not find order with orderId: %s", orderId));
    }
}
