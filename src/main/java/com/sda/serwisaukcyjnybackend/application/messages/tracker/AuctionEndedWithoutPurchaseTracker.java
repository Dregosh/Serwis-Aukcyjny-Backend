package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.auction.event.AuctionEndedWithoutPurchase;
import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class AuctionEndedWithoutPurchaseTracker {

    private static final String AUCTION_TITLE = "auctionTitle";
    private static final String AUCTION_URL = "auctionUrl";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(AuctionEndedWithoutPurchase event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(AUCTION_TITLE, event.getTitle());
        payload.put(AUCTION_URL, guiUrl + "/auction/" + event.getId());

        Message message = new Message(payload, MessageType.AUCTION_ENDED_WITHOUT_PURCHASE_MESSAGE, event.getEmail());
        messageRepository.save(message);
    }

}
