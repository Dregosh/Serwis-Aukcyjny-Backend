package com.sda.serwisaukcyjnybackend.application.messages;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageSenderScheduler {
    private final CustomMailSender mailSender;
    private final MessageRepository messageRepository;
    private final ITemplateEngine templateEngine;

    @Value("${app.message.maxTries}")
    private Integer maxTries;

    @Scheduled(cron = "${app.message.sendCron}")
    public void mailSend() {
        var messages = messageRepository.getAllByMessageStatusOrderByCreatedAtDesc(MessageStatus.CREATED);
        log.info("START SENDING MESSAGES - found {} messages to send", messages.size());
        messages.forEach(this::sendMail);
    }

    private void sendMail(Message message) {
        try {
            Context context = new Context();
            context.setVariables(message.getPayload());
            String content = templateEngine.process(message.getMessageType().name(), context);
            mailSender.sendMail(message.getEmail(), message.getMessageType().getSubject(), content);
            message.setMessageStatus(MessageStatus.SEND);
        } catch (Exception e) {
            if (message.getSendTries().equals(maxTries)) {
                message.setMessageStatus(MessageStatus.CANCELLED);
            } else {
                message.addSendTry();
            }
        }
        messageRepository.save(message);
    }
}
