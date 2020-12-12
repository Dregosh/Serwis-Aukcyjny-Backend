package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserEmailVerified;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class UserEmailVerifiedTracker {
    private static final String DISPLAY_NAME = "displayName";

    private final MessageRepository messageRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(UserEmailVerified event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(DISPLAY_NAME, event.getDisplayName());

        var message = new Message(payload, MessageType.INVITE_MESSAGE, event.getEmail());
        messageRepository.save(message);
    }
}
