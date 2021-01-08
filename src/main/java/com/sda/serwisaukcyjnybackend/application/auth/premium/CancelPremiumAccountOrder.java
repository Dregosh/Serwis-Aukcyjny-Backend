package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

@Value
public class CancelPremiumAccountOrder implements Command<String> {
    String orderId;
}
