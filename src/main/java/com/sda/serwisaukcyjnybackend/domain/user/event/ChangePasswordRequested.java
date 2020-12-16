package com.sda.serwisaukcyjnybackend.domain.user.event;

import lombok.Value;

@Value
public class ChangePasswordRequested {
    String displayName;
    String token;
    String email;
}
