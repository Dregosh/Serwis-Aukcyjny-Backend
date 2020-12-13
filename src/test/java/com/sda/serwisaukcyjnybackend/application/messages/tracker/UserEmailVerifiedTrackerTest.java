package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserEmailVerified;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEmailVerifiedTrackerTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    UserEmailVerifiedTracker tracker;

    @Test
    void shouldTrackEvent() {
        //given
        var event = new UserEmailVerified("test@test", "test");
        when(messageRepository.save(any())).thenReturn(new Message());

        //when && then
        tracker.trackEvent(event);
    }

}