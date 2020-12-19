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
    CHANGE_PASSWORD_MESSAGE("Zmiana hasła użytkownika"),
    BUY_NOW_PURCHASED_MESSAGE("Zakup przez Kup teraz"),
    BUY_NOW_SOLD_MESSAGE("Sprzedaż przez Kup teraz"),
    AUCTION_ENDED_WITHOUT_PURCHASE_MESSAGE("Aukcja zakończyła się bez ofert zakupu");

    private final String subject;
}
