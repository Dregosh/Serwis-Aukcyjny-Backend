package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.auction.event.AuctionCreated;
import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered;
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
public class AuctionCreatedTracker {

    private static final String AUCTION_TITLE = "auctionTitle";
    private static final String AUCTION_MIN_PRICE = "auctionMinPrice";
    private static final String AUCTION_BUY_NOW_PRICE = "auctionBuyNowPrice";
    private static final String AUCTION_START_DATE_TIME = "auctionStartDateTime";
    private static final String AUCTION_END_DATE_TIME = "auctionEndDateTime";
    private static final String AUCTION_URL = "auctionUrl";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(AuctionCreated event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(AUCTION_TITLE, event.getTitle());
        payload.put(AUCTION_MIN_PRICE, event.getMinPrice());
        payload.put(AUCTION_BUY_NOW_PRICE, event.getBuyNowPrice());
        payload.put(AUCTION_START_DATE_TIME, event.getStartDateTime());
        payload.put(AUCTION_END_DATE_TIME, event.getEndDateTime());
        payload.put(AUCTION_URL, guiUrl + "/auction/" + event.getId());

        var message = new Message(payload, MessageType.AUCTION_CREATED_MESSAGE, event.getEmail());
        messageRepository.save(message);
    }

}
