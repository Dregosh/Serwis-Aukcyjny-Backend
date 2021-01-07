package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

@Value
public class CompletePremiumAccountOrder implements Command<String> {
    String payerId;
    String paymentId;
}
