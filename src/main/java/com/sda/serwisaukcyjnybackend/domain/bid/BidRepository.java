package com.sda.serwisaukcyjnybackend.domain.bid;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Bid getByAuctionAndBidPrice(Auction auction, BigDecimal maxBidPrice);
    boolean existsByAuction(Auction auction);
}
