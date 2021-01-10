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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sda.serwisaukcyjnybackend.view.auction.AuctionSortFactory.createSort;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class AuctionService {
    private final AuctionRepository auctionRepository;
    private final ObservationRepository observationRepository;

    @Transactional(readOnly = true)
    public AuctionDTO getAuctionById(Long auctionId) {
        var observed = AuthenticatedService.getLoggedUserInfo()
                .map(userDetails -> observationRepository.existsByAuction_IdAndAndUser_Id(auctionId, userDetails.getUserId()))
                .orElse(false);
        return AuctionMapper.map(auctionRepository.getById(auctionId), observed);
    }

    @Transactional(readOnly = true)
    public Page<SimpleAuctionDTO> getSortedAuctionByCategory(Long categoryId, int page, int size,
                                                             AuctionSort sort, Map<String, String> filterMap) {
        var pageable = PageRequest.of(page, size, createSort(sort));
        var specification = AuctionSpecification.getFromAuctionFilterMap(filterMap)
                                                .categoryId(categoryId)
                                                .build();

        return auctionRepository.findAll(specification, pageable)
                .map(AuctionMapper::mapToSimpleAuction);
    }

    @Transactional(readOnly = true)
    public List<SimpleAuctionDTO> getUserAuction(Long userId) {
        return auctionRepository.findAllBySeller_Id(userId)
                                .stream().map(AuctionMapper::mapToSimpleAuction)
                                .collect(Collectors.toList());
    }

    public Page<SimpleAuctionDTO> getUserAuctionsSorted(Long userId, int page, int size,
                                                        AuctionSort sort,
                                                        Map<String, String> filterMap) {
        var pageable = PageRequest.of(page, size, createSort(sort));
        filterMap.put("ALL_STATUSES", "true");
        var specification =
                AuctionSpecification.getFromAuctionFilterMap(filterMap).build();
        Specification<Auction> sellerConstraint =
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("seller"), userId);
        var userAuctions =
                this.auctionRepository.findAll(where(specification).and(sellerConstraint), pageable);
        return userAuctions.map(AuctionMapper::mapToSimpleAuction);
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
