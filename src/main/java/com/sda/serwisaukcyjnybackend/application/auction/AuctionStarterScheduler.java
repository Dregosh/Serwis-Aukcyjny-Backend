package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuctionStarterScheduler {
    private final AuctionRepository auctionRepository;

    @Scheduled(cron = "${app.auction.expiredCheckCron}")
    public void checkForExpiredAuctions() {
        List<Auction> auctionsToStart = this.auctionRepository.findAllByStatusAndStartDateTimeBefore(AuctionStatus.CREATED, LocalDateTime.now());
        log.info("SCHEDULED CHECK FOR AUCTIONS TO START - found {} auctions to start", auctionsToStart.size());
        auctionsToStart.forEach(this::startAuction);
    }

    private void startAuction(Auction auction) {
        auction.setStarted();
        auctionRepository.save(auction);
    }
}
