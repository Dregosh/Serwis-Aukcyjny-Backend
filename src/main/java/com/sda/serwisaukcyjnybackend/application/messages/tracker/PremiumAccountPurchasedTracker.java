package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.event.PremiumAccountPurchased;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class PremiumAccountPurchasedTracker {
    private static final String DISPLAY_NAME = "displayName";
    private static final String PREMIUM_ACCOUNT_EXPIRATION = "premiumAccountExpiration";
    private static final String ACCOUNT_URL = "accountUrl";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(PremiumAccountPurchased event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(DISPLAY_NAME, event.getDisplayName());
        payload.put(PREMIUM_ACCOUNT_EXPIRATION, event.getPremiumAccountExpiration());
        payload.put(ACCOUNT_URL, guiUrl + "/auth/my-account");

        Message message = new Message(payload, MessageType.PREMIUM_ACCOUNT_PURCHASED, event.getEmail());
        messageRepository.save(message);
    }

}
