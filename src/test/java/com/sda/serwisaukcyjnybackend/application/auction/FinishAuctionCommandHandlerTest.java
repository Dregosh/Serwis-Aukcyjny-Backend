package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.bid.BidRepository;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.purchase.PurchaseRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinishAuctionCommandHandlerTest {
    @Mock
    AuctionRepository auctionRepository;
    @Mock
    PurchaseRepository purchaseRepository;
    @Mock
    BidRepository bidRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    FinishAuctionCommandHandler handler;

    @Test
    void shouldFinishPromotedAuctionWithPurchase() {
       //given
        var command = new FinishAuctionCommand(1L, Boolean.FALSE);
        when(auctionRepository.getOne(anyLong())).thenReturn(new Auction());
        when(auctionRepository.save(any())).thenReturn(prepareAuction(AuctionStatus.ENDED, BigDecimal.TEN, BigDecimal.ONE, true));
        when(userRepository.save(any())).thenReturn(new User());
        when(bidRepository.existsByAuction(any())).thenReturn(true);
        when(bidRepository.getByAuctionAndBidPrice(any(), any())).thenReturn(prepareBid(BigDecimal.TEN));
        when(purchaseRepository.save(any())).thenReturn(new Purchase());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldFinishNormalAuctionWithPurchase() {
        //given
        var command = new FinishAuctionCommand(1L, Boolean.FALSE);
        when(auctionRepository.getOne(anyLong())).thenReturn(new Auction());
        when(auctionRepository.save(any())).thenReturn(prepareAuction(AuctionStatus.ENDED, BigDecimal.TEN, BigDecimal.ONE, false));
        when(bidRepository.existsByAuction(any())).thenReturn(true);
        when(bidRepository.getByAuctionAndBidPrice(any(), any())).thenReturn(prepareBid(BigDecimal.TEN));
        when(purchaseRepository.save(any())).thenReturn(new Purchase());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldFinishPromotedAuctionWithoutPurchase() {
        //given
        var command = new FinishAuctionCommand(1L, Boolean.FALSE);
        when(auctionRepository.getOne(anyLong())).thenReturn(new Auction());
        when(auctionRepository.save(any())).thenReturn(prepareAuction(AuctionStatus.ENDED, BigDecimal.ONE, BigDecimal.TEN, true));
        when(bidRepository.existsByAuction(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(new User());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldFinishNormalAuctionWithoutPurchase() {
        //given
        var command = new FinishAuctionCommand(1L, Boolean.FALSE);
        when(auctionRepository.getOne(anyLong())).thenReturn(new Auction());
        when(auctionRepository.save(any())).thenReturn(prepareAuction(AuctionStatus.ENDED, BigDecimal.ONE, BigDecimal.TEN, false));
        when(bidRepository.existsByAuction(any())).thenReturn(false);

        //when && then
        handler.handle(command);
    }

    Auction prepareAuction(AuctionStatus status, BigDecimal maxBid, BigDecimal minPrice, boolean isPromoted) {
        Auction auction = new Auction();
        auction.setStatus(status);
        auction.setMaxBid(maxBid);
        auction.setMinPrice(minPrice);
        auction.setIsPromoted(isPromoted);
        auction.setSeller(new User());
        return auction;
    }

    Bid prepareBid(BigDecimal bidPrice) {
        Bid bid = new Bid();
        bid.setBidPrice(bidPrice);
        return bid;
    }

}
