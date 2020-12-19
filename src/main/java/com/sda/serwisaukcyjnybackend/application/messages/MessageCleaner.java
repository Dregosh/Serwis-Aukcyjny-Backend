package com.sda.serwisaukcyjnybackend.application.messages;

import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageCleaner {
    private final MessageRepository messageRepository;

    @Scheduled(cron = "${app.message.cleanCron}")
    public void cleanOldMessages() {
        var messages = messageRepository.findAllByMessageStatus(MessageStatus.SENT);
        log.info("START DELETING MESSAGES - found {} messages to delete", messages.size());
        messageRepository.deleteAll(messages);
    }
}
