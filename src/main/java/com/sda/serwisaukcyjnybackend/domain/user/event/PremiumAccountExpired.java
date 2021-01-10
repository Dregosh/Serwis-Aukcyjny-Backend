package com.sda.serwisaukcyjnybackend.domain.user.event;

import lombok.Value;

@Value
public class PremiumAccountExpired {
    String displayName;
    String email;
}
