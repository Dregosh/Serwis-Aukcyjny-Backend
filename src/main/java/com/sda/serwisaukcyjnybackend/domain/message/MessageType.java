package com.sda.serwisaukcyjnybackend.domain.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageType {
    REGISTER_MESSAGE("Rejestracja"),
    INVITE_MESSAGE("Witamy w aplikacji"),
    AUCTION_CREATED_MESSAGE("Aukcja wystawiona");

    private final String subject;
}
