package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.event.UpdateEmailRequested;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class UpdateEmailRequestedTracker {
    private static final String CONFIRM_URL = "confirmUrl";
    private static final String DISPLAY_NAME = "displayName";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(UpdateEmailRequested event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(CONFIRM_URL,
                    guiUrl + "/auth/email-change-confirm/" + event.getToken());
        payload.put(DISPLAY_NAME, event.getDisplayName());

        Message message = new Message(payload,
                                      MessageType.EMAIL_CHANGE_MESSAGE,
                                      event.getEmail());
        messageRepository.save(message);
    }
}
