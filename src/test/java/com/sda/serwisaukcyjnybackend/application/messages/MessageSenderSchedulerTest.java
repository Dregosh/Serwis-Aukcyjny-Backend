package com.sda.serwisaukcyjnybackend.application.messages;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.ITemplateEngine;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageSenderSchedulerTest {
    @Mock
    CustomMailSender mailSender;
    @Mock
    MessageRepository messageRepository;
    @Mock
    ITemplateEngine templateEngine;

    @InjectMocks
    MessageSenderScheduler messageSenderScheduler;

    @BeforeEach
    void setup() {
        messageSenderScheduler.maxTries = 2;
    }

    @ParameterizedTest
    @MethodSource("provideValues")
    void  shouldSendMessage(int tries, boolean shouldThrow, MessageStatus messageStatus) throws MessagingException {
        //given
        var message = new Message(new HashMap<>(), getMessageType(), "test@test");
        message.setSendTries(tries);
        when(messageRepository.getAllByMessageStatusOrderByCreatedAtDesc(MessageStatus.CREATED))
                .thenReturn(List.of(message));
        when(templateEngine.process(anyString(), any())).thenReturn("processedHtml");
        if (shouldThrow) {
            doThrow(MessagingException.class).when(mailSender).sendMail(any(), any(), any());
        } else {
            doNothing().when(mailSender).sendMail(any(), any(), any());
        }
        when(messageRepository.save(any())).thenReturn(message);

        //when
        messageSenderScheduler.mailSend();

        //then
        assertEquals(messageStatus, message.getMessageStatus());
    }

    private static Stream<Arguments> provideValues() {
        return Stream.of(
                Arguments.of(0, false, MessageStatus.SEND),
                Arguments.of(0, true, MessageStatus.CREATED),
                Arguments.of(2, true, MessageStatus.CANCELLED)
        );
    }

    private MessageType getMessageType() {
        return MessageType.values()[(new Random()).nextInt(MessageType.values().length)];
    }

}