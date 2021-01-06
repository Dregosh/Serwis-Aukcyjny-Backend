package com.sda.serwisaukcyjnybackend.application.pay;

import lombok.Value;

@Value
public class CreateOrderResponse {
    String orderId;
    String redirectUri;
}

