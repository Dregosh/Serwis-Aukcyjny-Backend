package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuctionFinisherScheduler {
    private final AuctionRepository auctionRepository;
    private final CommandDispatcher commandDispatcher;

    @Scheduled(cron = "${app.auction.expiredCheckCron}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkForExpiredAuctions() {
        List<Auction> auctionsToFinish =
                this.auctionRepository.findAllByStatusAndEndDateTimeBefore(AuctionStatus.STARTED, LocalDateTime.now());
        log.info("SCHEDULED CHECK FOR EXPIRED AUCTIONS - found {} auctions to finish", auctionsToFinish.size());
        auctionsToFinish.forEach(auction -> commandDispatcher.handle(new FinishAuctionCommand(auction.getId())));
    }
}
