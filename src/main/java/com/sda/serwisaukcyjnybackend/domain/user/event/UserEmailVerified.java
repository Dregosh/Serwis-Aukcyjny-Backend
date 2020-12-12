package com.sda.serwisaukcyjnybackend.domain.user.event;

import lombok.Value;

@Value
public class UserEmailVerified {
    String email;
    String displayName;
}
