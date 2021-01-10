package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotObserveAuctionException;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.category.Category;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.observation.ObservationRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObserveAuctionCommandHandlerTest {
    @Mock
    ObservationRepository observationRepository;
    @Mock
    AuctionRepository auctionRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    ObserveAuctionCommandHandler handler;

    @Test
    void shouldObserveIfAuctionCreated() {
        //given
        ObserveAuctionCommand command = new ObserveAuctionCommand(1L, 2L);
        Auction auction = createAuction(AuctionStatus.CREATED);
        User user = createUser(2L);
        when(auctionRepository.getOne(anyLong())).thenReturn(auction);
        when(userRepository.getOne(anyLong())).thenReturn(user);
        when(observationRepository.save(any())).thenReturn(new Observation());

        //when & then
        handler.handle(command);
    }

    @Test
    void shouldObserveIfAuctionStarted() {
        //given
        ObserveAuctionCommand command = new ObserveAuctionCommand(1L, 2L);
        Auction auction = createAuction(AuctionStatus.STARTED);
        User user = createUser(2L);
        when(auctionRepository.getOne(anyLong())).thenReturn(auction);
        when(userRepository.getOne(anyLong())).thenReturn(user);
        when(observationRepository.save(any())).thenReturn(new Observation());

        //when & then
        handler.handle(command);
    }

    @Test
    void shouldThrowExceptionIfAuctionEnded() {
        //given
        ObserveAuctionCommand command = new ObserveAuctionCommand(1L, 2L);
        Auction auction = createAuction(AuctionStatus.ENDED);
        User user = createUser(2L);
        when(auctionRepository.getOne(anyLong())).thenReturn(auction);
        when(userRepository.getOne(anyLong())).thenReturn(user);

        //when & then
        assertThatExceptionOfType(CannotObserveAuctionException.class)
                .isThrownBy(() -> handler.handle(command));
    }

    @Test
    void shouldThrowExceptionIfOwnAuction() {
        //given
        ObserveAuctionCommand command = new ObserveAuctionCommand(1L, 1L);
        Auction auction = createAuction(AuctionStatus.CREATED);
        User user = createUser(1L);
        when(auctionRepository.getOne(anyLong())).thenReturn(auction);
        when(userRepository.getOne(anyLong())).thenReturn(user);

        //when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> handler.handle(command));
    }

    Auction createAuction(AuctionStatus status) {
        Auction auction = new Auction(
                createUser(1L),
                "auction title",
                "auction description",
                BigDecimal.ONE,
                BigDecimal.TEN,
                Boolean.FALSE,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7L),
                new Category()
        );
        auction.setId(1L);
        auction.setStatus(status);
        return auction;
    }

    User createUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

}
