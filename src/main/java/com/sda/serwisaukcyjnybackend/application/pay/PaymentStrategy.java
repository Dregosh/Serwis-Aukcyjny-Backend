package com.sda.serwisaukcyjnybackend.application.pay;

public interface PaymentStrategy {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    CompleteOrderResponse completeOrder(CompleteOrderRequest request);
}
