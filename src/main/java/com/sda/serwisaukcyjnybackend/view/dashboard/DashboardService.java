package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.view.shared.AuctionMapper;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AuctionRepository auctionRepository;

    @Value("${app.dashboard.limit}")
    protected int limit = 10;

    public DashboardDTO getDashBoard() {
        List<SimpleAuctionDTO> lastAdded = this.getActiveAuctionsOrderByLatestCreated();

        List<SimpleAuctionDTO> nearlyEnd = this.getActiveAuctionsOrderByClosestToFinish();

        List<SimpleAuctionDTO> loggedUserAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> biddedAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> observedAuctions = new ArrayList<>();

        AuthenticatedService.getLoggedUserInfo().ifPresent(
                loggedUser -> {
                    Long loggedUserId = loggedUser.getUserId();
                    //TODO when getLoggedUserInfo works properly
                });

        //Mock START (loggedUser with ID=3)
        Long loggedUserId = 3L;
        loggedUserAuctions = this.getAllLoggedUserAuctionsOrderByEndTime(loggedUserId);
        biddedAuctions = this.getAllBiddedByLoggedUser(loggedUserId);
        observedAuctions = this.getLoggedUserObservedAuctions(loggedUserId);
        //Mock END

        List < SimpleAuctionDTO > justFinished = this.getFinishedAuctionsOrderByEndTime();

        return new DashboardDTO(lastAdded,
                                nearlyEnd,
                                loggedUserAuctions,
                                biddedAuctions,
                                observedAuctions,
                                justFinished);
    }

    public List<SimpleAuctionDTO> getActiveAuctionsOrderByLatestCreated() {
        return this.auctionRepository
                .findAllByStatusNotOrderByStartDateTimeDesc(
                        AuctionStatus.ENDED, PageRequest.of(0, this.limit))
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getActiveAuctionsOrderByClosestToFinish() {
        return this.auctionRepository
                .findAllByStatusNotOrderByEndDateTimeAsc(
                        AuctionStatus.ENDED, PageRequest.of(0, this.limit))
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getAllLoggedUserAuctionsOrderByEndTime(
            Long loggedUserId) {
        return this.auctionRepository.findAllBySeller_IdOrderByEndDateTimeAsc(
                loggedUserId, PageRequest.of(0, this.limit))
                                     .stream()
                                     .map(AuctionMapper::mapToSimpleAuction)
                                     .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getAllBiddedByLoggedUser(Long loggedUserId) {
        return this.auctionRepository
                .findAllByBids_User_Id(loggedUserId, PageRequest.of(0, this.limit))
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getLoggedUserObservedAuctions(Long loggedUserId) {
        return this.auctionRepository
                .findAllByObservations_User_IdOrderByEndDateTimeAsc(
                        loggedUserId, PageRequest.of(0, this.limit))
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getFinishedAuctionsOrderByEndTime() {
        return this.auctionRepository
                .findAllByStatusOrderByEndDateTimeDesc(
                        AuctionStatus.ENDED, PageRequest.of(0, this.limit))
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }
}
