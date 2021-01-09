package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.PremiumAccountPurchased;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PremiumAccountPurchasedTrackerTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    PremiumAccountPurchasedTracker tracker;

    @Test
    void shouldTrackEvent() {
        //given
        var event = new PremiumAccountPurchased("testName", "test@test", LocalDate.of(2021,1, 9));
        when(messageRepository.save(any())).thenReturn(new Message());

        //when && then
        tracker.trackEvent(event);
    }
}
