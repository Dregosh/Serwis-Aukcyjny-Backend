package com.sda.serwisaukcyjnybackend.application.messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomMailSender {
    private final JavaMailSender mailSender;

    public void sendMail(String sendTo, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(message, true);
        } catch (MessagingException e) {
            log.error("Could not send mail: {}", e.getMessage());
            throw e;
        }
        mailSender.send(mimeMessage);
    }
}
