package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.specification.AuctionSpecification;
import com.sda.serwisaukcyjnybackend.domain.observation.ObservationRepository;
import com.sda.serwisaukcyjnybackend.view.shared.AuctionMapper;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sda.serwisaukcyjnybackend.view.auction.AuctionSortFactory.createSort;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final ObservationRepository observationRepository;


    public AuctionDTO getAuctionById(Long auctionId) {
        boolean observed = AuthenticatedService.getLoggedUserInfo()
                .map(userDetails -> observationRepository.existsByAuction_IdAndAndUser_Id(auctionId, userDetails.getUserId()))
                .orElse(false);
        return AuctionMapper.map(auctionRepository.getById(auctionId), observed);
    }

    public Page<SimpleAuctionDTO> getSortedAuctionByCategory(Long categoryId, int page, int size,
                                                             AuctionSort sort, Map<AuctionFilter, ?> filterMap) {
        Pageable pageable = PageRequest.of(page, size, createSort(sort));
        var specification = AuctionSpecification.getFromAuctionFilterMap(filterMap)
                .categoryId(categoryId)
                .build();
        Page<Auction> auctions = auctionRepository.findAll(specification, pageable);
        return auctions.map(AuctionMapper::mapToSimpleAuction);
    }

    public List<SimpleAuctionDTO> getUserAuction(Long userId) {
        return auctionRepository.findAllBySeller_Id(userId)
                .stream().map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    public List<SimpleAuctionDTO> getObserved(Long userId) {
        return auctionRepository.findAllObserved(userId)
                .stream().map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }

    public List<SimpleAuctionDTO> getBidded(Long userId) {
        return auctionRepository.findAllBidded(userId)
                .stream().map(AuctionMapper::mapToSimpleAuction)
                .collect(Collectors.toList());
    }
}
