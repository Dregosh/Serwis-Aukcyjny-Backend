package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.view.dashboard.DashboardDTO;
import com.sda.serwisaukcyjnybackend.view.shared.AuctionMapper;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AuctionRepository auctionRepository;

    @Value("${app.dashboard.limit}")
    private int limit;

    public DashboardDTO getDashBoard() {
        List<SimpleAuctionDTO> lastAdded =
                auctionRepository.findAll().stream()
                                 .sorted(Comparator.comparing(Auction::getStartDateTime)
                                                   .reversed())
                                 .limit(this.limit)
                                 .map(AuctionMapper::mapToSimpleAuction)
                                 .collect(Collectors.toList());

        List<SimpleAuctionDTO> nearlyEnd =
                auctionRepository.findAll().stream()
                                 .sorted(Comparator.comparing(Auction::getEndDateTime))
                                 .limit(this.limit)
                                 .map(AuctionMapper::mapToSimpleAuction)
                                 .collect(Collectors.toList());

        //TODO logged user auctions
        List<SimpleAuctionDTO> loggedUserAuctions = new ArrayList<>();

        //TODO logged user bidded auctions
        List<SimpleAuctionDTO> biddedAuctions = new ArrayList<>();

        //TODO logged user observed auctions
        List<SimpleAuctionDTO> observedAuctions = new ArrayList<>();

        //TODO logged user finished auctions
        List<SimpleAuctionDTO> justFinished = new ArrayList<>();

        return new DashboardDTO(lastAdded,
                                nearlyEnd,
                                loggedUserAuctions,
                                biddedAuctions,
                                observedAuctions,
                                justFinished);
    }
}
