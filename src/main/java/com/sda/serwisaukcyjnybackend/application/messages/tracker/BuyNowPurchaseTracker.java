package com.sda.serwisaukcyjnybackend.application.messages.tracker;

import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.purchase.event.BuyNowPurchase;
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
public class BuyNowPurchaseTracker {

    private static final String OTHER_PARTY_NAME = "otherPartyName";
    private static final String AUCTION_TITLE = "auctionTitle";
    private static final String PRICE = "price";
    private static final String AUCTION_URL = "auctionUrl";
    private static final String PURCHASE_URL = "purchaseUrl";

    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional
    public void trackEvent(BuyNowPurchase event) {
        Message buyerMessage = createBuyerMessage(event);
        messageRepository.save(buyerMessage);
        Message sellerMessage = createSellerMessage(event);
        messageRepository.save(sellerMessage);
    }

    private Message createBuyerMessage(BuyNowPurchase event) {
        HashMap<String, Object> payload = createCommonPayload(event);
        payload.put(OTHER_PARTY_NAME, event.getSellerName());
        return new Message(payload, MessageType.BUY_NOW_PURCHASED_MESSAGE, event.getBuyerEmail());
    }

    private Message createSellerMessage(BuyNowPurchase event) {
        HashMap<String, Object> payload = createCommonPayload(event);
        payload.put(OTHER_PARTY_NAME, event.getBuyerName());
        return new Message(payload, MessageType.BUY_NOW_SOLD_MESSAGE, event.getSellerEmail());
    }

    private HashMap<String, Object> createCommonPayload(BuyNowPurchase event) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(AUCTION_TITLE, event.getAuctionTitle());
        payload.put(PRICE, event.getPrice());
        payload.put(AUCTION_URL, guiUrl + "/auction/" + event.getAuctionId());
        payload.put(PURCHASE_URL, guiUrl + "/purchase/" + event.getPurchaseId());
        return payload;
    }

}
