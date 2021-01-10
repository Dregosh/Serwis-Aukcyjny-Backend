package com.sda.serwisaukcyjnybackend.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends PagingAndSortingRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    @EntityGraph("auction-bids-purchase")
    List<Auction> findFirst10BySeller_IdOrderByEndDateTimeAsc(Long sellerId, Sort sort);


    @EntityGraph("auction-bids-purchase")
    List<Auction> findFirst10ByStatusNotOrderByStartDateTimeDesc(AuctionStatus status, Sort sort);

    @EntityGraph("auction-bids-purchase")
    List<Auction> findFirst10ByStatusNotOrderByEndDateTimeAsc(AuctionStatus status, Sort sort);

    @EntityGraph("auction-bids-purchase")
    List<Auction> findFirst10ByStatusOrderByEndDateTimeDesc(AuctionStatus status, Sort sort);

    @EntityGraph("auction-bids-purchase")
    List<Auction> findFirst10ByObservations_User_IdOrderByEndDateTimeAsc(Long observerId, Sort sort);

    @EntityGraph("auction-bids-purchase")
    List<Auction> findFirst10ByBids_User_Id(Long bidderId, Sort sort);

    List<Auction> findAllByStatusAndEndDateTimeBefore(AuctionStatus status, LocalDateTime now);

    List<Auction> findAllByStatusAndStartDateTimeBefore(AuctionStatus status, LocalDateTime now);

    @EntityGraph("auction-bids-purchase")
    List<Auction> findAllBySeller_Id(Long sellerId);

    @EntityGraph("auction-bids-purchase")
    @Query("select a from Auction a, in(a.bids) b " +
            "where b.user.id = :userId")
    List<Auction> findAllBidded(@Param("userId") Long userId);

    @EntityGraph("auction-bids-purchase")
    @Query("select a from Auction a, in(a.observations) o " +
            "where o.user.id = :userId")
    List<Auction> findAllObserved(@Param("userId") Long userId);

    @EntityGraph("auction-seller")
    Auction getById(Long auctionId);

    @Override
    @NonNull
    @EntityGraph("auction-bids-purchase")
    Page<Auction> findAll(@Nullable Specification<Auction> spec, @Nullable Pageable pageable);

    @Query("select a from Auction a where a.id = :id")
    Auction getOne(@Param("id") Long id);

    @Query("select a from Auction a " +
            "where a.id = :id and a.seller.id = :sellerId")
    Optional<Auction> findAuctionByIdAndSellerId(@Param("id") Long auctionId,
                                                  @Param("sellerId") Long sellerId);

    List<Auction> findAllByStatusNotAndEndDateTimeBetween(AuctionStatus status,
                                                       LocalDateTime earlierDate, LocalDateTime laterDate);

}
