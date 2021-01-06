package com.sda.serwisaukcyjnybackend.application.pay;

import lombok.Value;

@Value
public class CompleteOrderRequest {
    String payerId;
    String paymentId;
}
