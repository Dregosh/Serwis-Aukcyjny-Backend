package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.bid.BidRepository;
import com.sda.serwisaukcyjnybackend.domain.category.Category;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.OptimisticLockException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BidAuctionCommandHandlerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    AuctionRepository auctionRepository;
    @Mock
    BidRepository bidRepository;

    @InjectMocks
    BidAuctionCommandHandler handler;


    @Test
    void shouldBidAuction() {
        //given
        var command = new BidAuctionCommand(1L, BigDecimal.ONE, 1L);
        var auction = prepareAuction();
        var user = prepareUser(2L);
        auction.setStatus(AuctionStatus.STARTED);
        when(auctionRepository.getOne(anyLong())).thenReturn(auction);
        when(userRepository.getOne(anyLong())).thenReturn(user);
        when(auctionRepository.save(any())).thenReturn(new Auction());
        when(bidRepository.save(any())).thenReturn(new Bid());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldBidAfterAnotherBid() {
        //given
        var command = new BidAuctionCommand(1L, BigDecimal.TEN, 1L);
        var auction = prepareAuction();
        updateAuction(auction, AuctionStatus.STARTED, 1L);
        var alreadyBiddedAuction = prepareAuction();
        alreadyBiddedAuction.setMaxBid(BigDecimal.ONE);
        updateAuction(alreadyBiddedAuction, AuctionStatus.STARTED, 1L);
        var user = prepareUser(2L);
        doReturn(auction).doReturn(alreadyBiddedAuction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);
        doThrow(OptimisticLockException.class).doReturn(new Auction()).when(auctionRepository).save(any());
        when(bidRepository.save(any())).thenReturn(new Bid());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldNotBidIfAnotherBidIsBigger() {
        //given
        var command = new BidAuctionCommand(1L, BigDecimal.ONE, 1L);
        var auction = prepareAuction();
        updateAuction(auction, AuctionStatus.STARTED, 1L);
        var alreadyBiddedAuction = prepareAuction();
        alreadyBiddedAuction.setMaxBid(BigDecimal.TEN);
        updateAuction(alreadyBiddedAuction, AuctionStatus.STARTED, 1L);
        var user = prepareUser(2L);
        doReturn(auction).doReturn(alreadyBiddedAuction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);
        doThrow(OptimisticLockException.class).when(auctionRepository).save(any());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldNotBidIfAuctionIsBought() {
        //given
        var command = new BidAuctionCommand(1L, BigDecimal.ONE, 1L);
        var auction = prepareAuction();
        updateAuction(auction, AuctionStatus.STARTED, 1L);
        var alreadyBiddedAuction = prepareAuction();
        alreadyBiddedAuction.setMaxBid(BigDecimal.TEN);
        updateAuction(alreadyBiddedAuction, AuctionStatus.ENDED, 1L);
        var user = prepareUser(2L);
        doReturn(auction).doReturn(alreadyBiddedAuction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);
        doThrow(OptimisticLockException.class).when(auctionRepository).save(any());

        //when && then
        handler.handle(command);
    }

    @Test
    void userCantBidOwnAuction() {
        //given
        var command = new BidAuctionCommand(1L, BigDecimal.ONE, 1L);
        var auction = prepareAuction();
        updateAuction(auction, AuctionStatus.STARTED, 1L);
        var user = prepareUser(12L);
        doReturn(auction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);

        //when && then
        handler.handle(command);
    }

    Auction prepareAuction() {
        return new Auction(prepareUser(1L), "title", "description", BigDecimal.ZERO,
                BigDecimal.TEN, false, LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                new Category());
    }

    User prepareUser(Long userId) {
        var user = new User();
        user.setId(userId);
        return user;
    }

    void updateAuction(Auction auction, AuctionStatus status, Long id) {
        auction.setId(id);
        auction.setStatus(status);
    }

}