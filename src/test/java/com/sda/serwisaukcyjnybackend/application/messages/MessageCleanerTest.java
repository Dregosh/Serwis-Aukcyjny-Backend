package com.sda.serwisaukcyjnybackend.application.messages;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageCleanerTest {
    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    MessageCleaner messageCleaner;

    @Test
    void shouldCleanMessages() {
        //given
        var message = new Message();
        when(messageRepository.findAllByMessageStatus(MessageStatus.SEND)).thenReturn(List.of(message));
        doNothing().when(messageRepository).deleteAll(anyCollection());

        //when && then
        messageCleaner.cleanOldMessages();
    }
}