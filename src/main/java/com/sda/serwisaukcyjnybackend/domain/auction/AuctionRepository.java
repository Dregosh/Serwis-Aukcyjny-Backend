package com.sda.serwisaukcyjnybackend.domain.auction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

//    List<Auction> getAllOrderByStartDateTimeDesc(Pageable pageable);




}
