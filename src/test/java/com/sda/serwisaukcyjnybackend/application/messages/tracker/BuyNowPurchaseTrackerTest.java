package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.purchase.event.BuyNowPurchase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyNowPurchaseTrackerTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    BuyNowPurchaseTracker buyNowPurchaseTracker;

    @Test
    void shouldTrackEvent() {
        //given
        BuyNowPurchase event = new BuyNowPurchase(
                1L,
                "test@test",
                "seller's name",
                1L,
                "auction title",
                BigDecimal.ONE
        );
        when(messageRepository.save(any())).thenReturn(new Message());

        //when & then
        buyNowPurchaseTracker.trackEvent(event);
    }

}
