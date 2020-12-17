package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.auction.event.AuctionCreated;
import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionCreatedTrackerTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    AuctionCreatedTracker auctionCreatedTracker;

    @Test
    void shouldTrackEvent() {
        // given
        var event = new AuctionCreated(
                "test@test",
                1L,
                "auction title",
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5),
                LocalDateTime.of(2020, 12, 1, 12,0),
                LocalDateTime.of(2020, 12, 8, 12, 0)
        );
        when(messageRepository.save(any())).thenReturn(new Message());

        // when & then
        auctionCreatedTracker.trackEvent(event);
    }

}
