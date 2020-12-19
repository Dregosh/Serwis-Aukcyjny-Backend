package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.auction.event.AuctionEndedWithoutPurchase;
import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionEndedWithoutPurchaseTrackerTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    AuctionEndedWithoutPurchaseTracker tracker;

    @Test
    void shouldTrackEvent() {
        //given
        AuctionEndedWithoutPurchase event = new AuctionEndedWithoutPurchase(
          "test@test",
          1L,
          "auction title"
        );
        when(messageRepository.save(any())).thenReturn(new Message());

        //when & then
        tracker.trackEvent(event);
    }

}
