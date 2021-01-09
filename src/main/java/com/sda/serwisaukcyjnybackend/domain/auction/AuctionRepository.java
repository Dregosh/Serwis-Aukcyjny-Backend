package com.sda.serwisaukcyjnybackend.domain.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends PagingAndSortingRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

    List<AuctionId> findAllBySeller_IdOrderByEndDateTimeAsc(
            @Param("sellerId") Long sellerId, Pageable pageable);


    List<AuctionId> findAllByStatusNotOrderByStartDateTimeDesc(
            @Param("auctionStatus") AuctionStatus status, Pageable pageable);

    List<AuctionId> findAllByStatusNotOrderByEndDateTimeAsc(
            @Param("auctionStatus") AuctionStatus status, Pageable pageable);

    List<AuctionId> findAllByStatusOrderByEndDateTimeDesc(
            AuctionStatus status, Pageable pageable);

    List<AuctionId> findAllByObservations_User_IdOrderByEndDateTimeAsc(
            Long observerId, Pageable pageable);

    List<AuctionId> findAllByBids_User_Id(Long bidderId, Pageable pageable);

    @EntityGraph("auction-photos")
    List<Auction> findAllByStatusAndEndDateTimeBefore(AuctionStatus status, LocalDateTime now);

    @EntityGraph("auction-photos")
    List<Auction> findAllByStatusAndStartDateTimeBefore(AuctionStatus status, LocalDateTime now);

    @EntityGraph("auction-photos-seller")
    List<Auction> findAllBySeller_Id(Long sellerId);

    @EntityGraph("auction-photos")
    @Query("select a from Auction a, in(a.bids) b " +
            "where b.user.id = :userId")
    List<Auction> findAllBidded(@Param("userId") Long userId);

    @EntityGraph("auction-photos")
    @Query("select a from Auction a, in(a.observations) o " +
            "where o.user.id = :userId")
    List<Auction> findAllObserved(@Param("userId") Long userId);

    @EntityGraph("auction-photos-seller")
    Auction getById(Long auctionId);

    @Override
    @NonNull
    @EntityGraph("auction-photos-purchase")
    Page<Auction> findAll(@Nullable Specification<Auction> spec, @Nullable Pageable pageable);

    @Query("select a from Auction a where a.id = :id")
    Auction getOne(@Param("id") Long id);

    @Query("select a from Auction a " +
            "where a.id = :id and a.seller.id = :sellerId")
    Optional<Auction> findAuctionByIdAndSellerId(@Param("id") Long auctionId,
                                                  @Param("sellerId") Long sellerId);

    List<Auction> findAllByStatusNotAndEndDateTimeBetween(AuctionStatus status,
                                                       LocalDateTime earlierDate, LocalDateTime laterDate);

    @EntityGraph("auction-photos-purchase")
    Collection<Auction> findAuctionsByIdIn(Collection<Long> ids);

}
