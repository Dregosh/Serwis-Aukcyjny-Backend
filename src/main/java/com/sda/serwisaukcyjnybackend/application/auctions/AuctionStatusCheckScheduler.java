package com.sda.serwisaukcyjnybackend.application.auctions;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuctionStatusCheckScheduler {
    private final AuctionRepository auctionRepository;

    @Scheduled(cron = "${app.auctions.expiredCheckCron}")
    public void checkForExpiredAuctions() {
        List<Auction> allAuctions = this.auctionRepository.findAll();
        AtomicInteger expiredCount = new AtomicInteger();
        allAuctions.forEach(auction -> {
            if (auction.getEndDateTime().isBefore(LocalDateTime.now()) &&
                !auction.getStatus().equals(AuctionStatus.ENDED)) {
                log.info("Auction with ID {} has just ended", auction.getId());
                auction.setStatus(AuctionStatus.ENDED);
                this.auctionRepository.save(auction);
                expiredCount.getAndIncrement();
            }
        });
        log.info("SCHEDULED CHECK FOR EXPIRED AUCTIONS - found {} expired auction(s)",
                 expiredCount.get());
    }
}
