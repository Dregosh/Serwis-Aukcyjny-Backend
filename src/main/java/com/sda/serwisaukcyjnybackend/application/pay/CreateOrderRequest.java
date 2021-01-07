package com.sda.serwisaukcyjnybackend.application.pay;

import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.Value;

@Value
public class CreateOrderRequest {
    User user;
}
