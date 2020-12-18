package com.sda.serwisaukcyjnybackend.domain.auction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findAllBySeller_IdOrderByEndDateTimeAsc(
            Long sellerId, Pageable pageable);

    List<Auction> findAllByStatusNotOrderByStartDateTimeDesc(
            AuctionStatus status, Pageable pageable);

    List<Auction> findAllByStatusNotOrderByEndDateTimeAsc(
            AuctionStatus status, Pageable pageable);

    List<Auction> findAllByStatusOrderByEndDateTimeDesc(
            AuctionStatus status, Pageable pageable);

    List<Auction> findAllByObservations_User_IdOrderByEndDateTimeAsc(
            Long observerId, Pageable pageable);

    List<Auction> findAllByBids_User_Id(Long bidderId, Pageable pageable);

    List<Auction> findAllByStatus(AuctionStatus status);

    List<Auction> findAllByStatusAndEndDateTimeBefore(AuctionStatus status, LocalDateTime now);

    List<Auction> findAllByStatusAndStartDateTimeBefore(AuctionStatus status, LocalDateTime now);
}
