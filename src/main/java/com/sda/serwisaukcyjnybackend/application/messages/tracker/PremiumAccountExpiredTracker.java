package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.event.PremiumAccountExpired;
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
public class PremiumAccountExpiredTracker {
    private static final String DISPLAY_NAME = "displayName";
    private static final String ACCOUNT_URL = "accountUrl";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(PremiumAccountExpired event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(DISPLAY_NAME, event.getDisplayName());
        payload.put(ACCOUNT_URL, guiUrl + "/auth/my-account");

        Message message = new Message(payload, MessageType.PREMIUM_ACCOUNT_EXPIRED, event.getEmail());
        messageRepository.save(message);
    }
}
