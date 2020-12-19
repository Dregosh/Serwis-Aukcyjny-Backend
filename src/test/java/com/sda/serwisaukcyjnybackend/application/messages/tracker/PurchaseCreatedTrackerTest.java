package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.purchase.event.PurchaseCreated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseCreatedTrackerTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    PurchaseCreatedTracker purchaseCreatedTracker;

    @Test
    void shouldTrackEvent() {
        //given
        PurchaseCreated event = new PurchaseCreated(
                1L,
                "seller@test",
                "buyer@test",
                "seller's name",
                "buyer's name",
                1L,
                "auction title",
                BigDecimal.ONE,
                Boolean.FALSE
        );
        when(messageRepository.save(any())).thenReturn(new Message());

        //when & then
        purchaseCreatedTracker.trackEvent(event);
    }

}
