package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionId;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.view.shared.AuctionMapper;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sda.serwisaukcyjnybackend.view.auction.AuctionSortFactory.createSort;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AuctionRepository auctionRepository;

    public DashboardDTO getDashBoard() {
        List<SimpleAuctionDTO> lastAdded = this.getActiveAuctionsOrderByLatestCreated();

        List<SimpleAuctionDTO> nearlyEnd = this.getActiveAuctionsOrderByClosestToFinish();

        List<SimpleAuctionDTO> loggedUserAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> biddedAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> observedAuctions = new ArrayList<>();
        List<SimpleAuctionDTO> justFinished = this.getFinishedAuctionsOrderByEndTime();

        var dashboard = new DashboardDTO(lastAdded, nearlyEnd, loggedUserAuctions,
                biddedAuctions, observedAuctions, justFinished);

        return appendLoggedUserAuctionsAndGet(dashboard);
    }

    private DashboardDTO appendLoggedUserAuctionsAndGet(DashboardDTO dashboardDTO) {
        AuthenticatedService.getLoggedUserInfo().ifPresent(
                loggedUser -> {
                    Long loggedUserId = loggedUser.getUserId();
                    dashboardDTO.setLoggedUserAuctions(this.getAllLoggedUserAuctionsOrderByEndTime(loggedUserId));
                    dashboardDTO.setBiddedAuctions(this.getAllBiddedByLoggedUser(loggedUserId));
                    dashboardDTO.setObservedAuctions(this.getLoggedUserObservedAuctions(loggedUserId));
                });
        return dashboardDTO;
    }

    public List<SimpleAuctionDTO> getActiveAuctionsOrderByLatestCreated() {
        return auctionRepository.findFirst10ByStatusNotOrderByStartDateTimeDesc(AuctionStatus.ENDED, createSort())
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getActiveAuctionsOrderByClosestToFinish() {
        return this.auctionRepository.findFirst10ByStatusNotOrderByEndDateTimeAsc(AuctionStatus.ENDED, createSort())
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getAllLoggedUserAuctionsOrderByEndTime(Long loggedUserId) {
        return this.auctionRepository.findFirst10BySeller_IdOrderByEndDateTimeAsc(loggedUserId, createSort())
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getAllBiddedByLoggedUser(Long loggedUserId) {
        return this.auctionRepository.findFirst10ByBids_User_Id(loggedUserId, createSort())
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getLoggedUserObservedAuctions(Long loggedUserId) {
        return this.auctionRepository
                .findFirst10ByObservations_User_IdOrderByEndDateTimeAsc(loggedUserId,  createSort())
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    private List<SimpleAuctionDTO> getFinishedAuctionsOrderByEndTime() {
        return this.auctionRepository.findFirst10ByStatusOrderByEndDateTimeDesc(
                AuctionStatus.ENDED, createSort())
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }
}
