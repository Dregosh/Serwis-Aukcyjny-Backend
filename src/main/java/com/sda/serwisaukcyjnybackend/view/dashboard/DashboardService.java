package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.view.dashboard.DashboardDTO;
import com.sda.serwisaukcyjnybackend.view.shared.AuctionMapper;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final AuctionRepository auctionRepository;

    @Value("${app.dashboard.limit}")
    private int limit;

    public DashboardDTO getDashBoard() {
        List<SimpleAuctionDTO> lastAdded = auctionRepository.findAll(PageRequest.of(0, limit))
                .stream()
                .map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());

        return new DashboardDTO();
    }
}
