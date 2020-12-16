package com.sda.serwisaukcyjnybackend.domain.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MessageType {
    REGISTER_MESSAGE("Rejestracja"),
    INVITE_MESSAGE("Witamy w aplikacji"),
    AUCTION_CREATED_MESSAGE("Aukcja wystawiona"),
    EMAIL_CHANGE_MESSAGE("Zmiana adresu e-mail"),
    CHANGE_PASSWORD_MESSAGE("Zmiana hasła użytkownika");

    private final String subject;
}
