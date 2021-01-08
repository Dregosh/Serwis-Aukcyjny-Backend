package com.sda.serwisaukcyjnybackend.application.pay;

public class PaymentExecutionException extends RuntimeException{
    public PaymentExecutionException(String message) {
        super(message);
    }
}
