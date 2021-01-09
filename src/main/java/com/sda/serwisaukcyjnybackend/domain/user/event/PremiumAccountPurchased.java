package com.sda.serwisaukcyjnybackend.domain.user.event;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PremiumAccountPurchased {
    String displayName;
    String email;
    LocalDate premiumAccountExpiration;
}
