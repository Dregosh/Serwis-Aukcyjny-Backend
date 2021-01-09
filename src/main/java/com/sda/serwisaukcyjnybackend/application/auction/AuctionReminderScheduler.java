package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.message.Message;
import com.sda.serwisaukcyjnybackend.domain.message.MessageRepository;
import com.sda.serwisaukcyjnybackend.domain.message.MessageType;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuctionReminderScheduler {

    private static final String AUCTION_TITLE = "auctionTitle";
    private static final String AUCTION_CURRENT_PRICE = "auctionCurrentPrice";
    private static final String AUCTION_END_DATE_TIME = "auctionEndDateTime";
    private static final String AUCTION_URL = "auctionUrl";

    private final AuctionRepository auctionRepository;
    private final MessageRepository messageRepository;

    @Value("${app.web.guiUrl}")
    private String guiUrl;

    @Scheduled(cron = "${app.auction.reminderCheckCron}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkForRemindersToSend() {
        List<Auction> auctionsToSendReminderAbout = getObservedAuctionsEndingBetween23And24Hours();
        log.info("SCHEDULED CHECK FOR REMINDERS TO SEND - found {} auctions to remind about", auctionsToSendReminderAbout.size());
        auctionsToSendReminderAbout.forEach(this::handleAuctionReminders);
    }

    private List<Auction> getObservedAuctionsEndingBetween23And24Hours() {
        LocalDateTime currentTime = LocalDateTime.now();
        List<Auction> auctions = this.auctionRepository.findAllByStatusNotAndEndDateTimeBetween(AuctionStatus.ENDED,
                currentTime.plusHours(23L), currentTime.plusDays(1L));
        auctions = auctions.stream().filter(Auction::hasNotEmptyObservations).collect(Collectors.toList());
        return auctions;
    }

    private void handleAuctionReminders(Auction auction) {
        HashMap<String, Object> payload = createMessagePayload(auction);
        List<User> usersToSendReminderTo = getUsersToSendReminderTo(auction);
        for(User user : usersToSendReminderTo) {
            addMessageToSend(payload, user.getEmail());
        }
    }

    private List<User> getUsersToSendReminderTo(Auction auction) {
        List<User> users = new ArrayList<>();
        auction.getObservations().forEach(observation -> users.add(observation.getUser()));
        return users;
    }

    private HashMap<String, Object> createMessagePayload(Auction auction) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(AUCTION_TITLE, auction.getTitle());
        payload.put(AUCTION_CURRENT_PRICE, auction.getMaxBid());
        payload.put(AUCTION_END_DATE_TIME, auction.getEndDateTime());
        payload.put(AUCTION_URL, guiUrl + "/auction/" + auction.getId());
        return payload;
    }

    private void addMessageToSend(HashMap<String, Object> payload, String email) {
        Message message = new Message(payload, MessageType.AUCTION_ENDING_REMINDER, email);
        messageRepository.save(message);
    }

}
