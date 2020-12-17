package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.ChangePasswordRequested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangePasswordRequestedTrackerTest {
    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    ChangePasswordRequestedTracker tracker;

    @Test
    void shouldTrackEvent() {
        //given
        ChangePasswordRequested event =
                new ChangePasswordRequested("sampleName", "sampleToken", "test@emailpl");
        when(messageRepository.save(any())).thenReturn(new Message());

        //when & then
        tracker.trackEvent(event);
    }
}
