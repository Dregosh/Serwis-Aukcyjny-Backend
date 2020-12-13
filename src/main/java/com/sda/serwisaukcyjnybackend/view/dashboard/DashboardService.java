package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.view.shared.AuctionMapper;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AuctionRepository auctionRepository;

    @Value("${app.dashboard.limit}")
    private int limit;

    public DashboardDTO getDashBoard() {
        List<SimpleAuctionDTO> lastAdded = this.getActiveAuctionsOrderByLatestCreated();
        List<SimpleAuctionDTO> nearlyEnd = this.getActiveAuctionsOrderByClosestToFinish();

        List<SimpleAuctionDTO> loggedUserAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> biddedAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> observedAuctions = new ArrayList<>();

        AuthenticatedService.getLoggedUserInfo().ifPresent(
                loggedUser -> {
                    Long loggedUserId = loggedUser.getUserId();

                    //TODO: assign logged user's three auction lists:
                    //      loggedUserAuctions, biddedAuctions, observedAuctions
                });

        List<SimpleAuctionDTO> justFinished = getFinishedAuctionsOrderByEndTime();

        return new DashboardDTO(lastAdded,
                                nearlyEnd,
                                loggedUserAuctions,
                                biddedAuctions,
                                observedAuctions,
                                justFinished);
    }

    private List<SimpleAuctionDTO> getActiveAuctionsOrderByLatestCreated() {
        return auctionRepository.findAll().stream()
                                .filter(this.isAuctionActive())
                                .sorted(Comparator.comparing(Auction::getStartDateTime)
                                                  .reversed())
                                .limit(this.limit)
                                .map(AuctionMapper::mapToSimpleAuction)
                                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getActiveAuctionsOrderByClosestToFinish() {
        return auctionRepository.findAll().stream()
                                .filter(this.isAuctionActive())
                                .sorted(Comparator.comparing(Auction::getEndDateTime))
                                .limit(this.limit)
                                .map(AuctionMapper::mapToSimpleAuction)
                                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getFinishedAuctionsOrderByEndTime() {
        return auctionRepository.findAll().stream()
                                .filter(this.isAuctionFinished())
                                .sorted(Comparator.comparing(Auction::getEndDateTime))
                                .limit(this.limit)
                                .map(AuctionMapper::mapToSimpleAuction)
                                .collect(Collectors.toList());
    }

    private Predicate<Auction> isAuctionFinished() {
        return auction -> auction.getEndDateTime().isBefore(LocalDateTime.now());
    }

    private Predicate<Auction> isAuctionActive() {
        return auction -> auction.getEndDateTime().isAfter(LocalDateTime.now());
    }
}
