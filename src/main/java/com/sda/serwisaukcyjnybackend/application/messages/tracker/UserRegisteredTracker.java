package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered;
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
public class UserRegisteredTracker {
    private static final String CONFIRM_URL = "confirmUrl";
    private static final String DISPLAY_NAME = "displayName";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(UserRegistered event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(CONFIRM_URL, guiUrl + "/verification/" + event.getToken());
        payload.put(DISPLAY_NAME, event.getDisplayName());

        var message = new Message(payload, MessageType.REGISTER_MESSAGE, event.getEmail());
        messageRepository.save(message);
    }
}
