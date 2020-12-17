package com.sda.serwisaukcyjnybackend.domain.auction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>, JpaSpecificationExecutor<Auction> {

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
}
