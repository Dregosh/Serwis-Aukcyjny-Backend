package com.sda.serwisaukcyjnybackend.domain.user.event;

import lombok.Value;

@Value
public class UpdateEmailRequested {
    String displayName;
    String token;
    String email;
}
