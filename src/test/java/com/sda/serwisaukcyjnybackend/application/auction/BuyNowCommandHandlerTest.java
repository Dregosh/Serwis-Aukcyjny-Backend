package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotBuyNowException;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.category.Category;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.purchase.PurchaseRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyNowCommandHandlerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    AuctionRepository auctionRepository;
    @Mock
    PurchaseRepository purchaseRepository;
    
    @InjectMocks
    BuyNowCommandHandler handler;

    @Test
    void shouldBuyNow() {
        //given
        var command = new BuyNowCommand(1L,1L);
        var auction = prepareAuction();
        var user = prepareUser(2L);
        when(auctionRepository.getOne(anyLong())).thenReturn(auction);
        when(userRepository.getOne(anyLong())).thenReturn(user);
        when(auctionRepository.save(any())).thenReturn(new Auction());
        when(purchaseRepository.save(any())).thenReturn(new Purchase());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldNotBuyIfAuctionStarted() {
        //given
        var command = new BuyNowCommand(1L,1L);
        var auction = prepareAuction();
        updateAuction(auction, AuctionStatus.STARTED, 1L);
        var user = prepareUser(2L);
        doReturn(auction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);

        //when && then
        assertThrows(CannotBuyNowException.class, () -> handler.handle(command));
    }

    @Test
    void shouldNotBuyNowIfAuctionChanged() {
        //given
        var command = new BuyNowCommand(1L,1L);
        var auction = prepareAuction();
        var user = prepareUser(2L);
        doReturn(auction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);
        doThrow(OptimisticLockException.class).when(auctionRepository).save(any());

        //when && then
        assertThrows(CannotBuyNowException.class, () -> handler.handle(command));
    }

    @Test
    void userCantBidOwnAuction() {
        //given
        var command = new BuyNowCommand(1L,1L);
        var auction = prepareAuction();
        var user = prepareUser(1L);
        doReturn(auction).when(auctionRepository).getOne(anyLong());
        when(userRepository.getOne(anyLong())).thenReturn(user);

        //when && then
        assertThrows(IllegalArgumentException.class, () ->handler.handle(command));
    }


    Auction prepareAuction() {
        return new Auction(prepareUser(1L), "title", "description", BigDecimal.ZERO,
                BigDecimal.TEN, false, LocalDateTime.now(), LocalDateTime.now().plusDays(7), new Category());
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