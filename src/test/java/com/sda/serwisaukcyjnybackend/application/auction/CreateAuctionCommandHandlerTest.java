package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.category.Category;
import com.sda.serwisaukcyjnybackend.domain.category.CategoryRepository;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.AccountType;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAuctionCommandHandlerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    AuctionRepository auctionRepository;
    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CreateAuctionCommandHandler handler;

    @BeforeEach
    void setup() {
        handler.duration = 14;
        handler.maxPromoted = 10;
    }

    @Test
    void shouldCreatePromotedAuction() {
        //given
        var command = prepareCommand(true);
        when(userRepository.getOne(anyLong())).thenReturn(prepareUser(AccountType.PREMIUM, 2));
        when(userRepository.save(any())).thenReturn(new User());
        when(auctionRepository.save(any())).thenReturn(new Auction());
        when(categoryRepository.getOne(anyLong())).thenReturn(new Category());

        //when && then
        handler.handle(command);

    }

    @Test
    void shouldCreateNormalAuction() {
        //given
        var command = prepareCommand(false);
        when(userRepository.getOne(anyLong())).thenReturn(prepareUser(AccountType.NORMAL, 0));
        when(categoryRepository.getOne(anyLong())).thenReturn(new Category());
        when(auctionRepository.save(any())).thenReturn(new Auction());

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldThrowExceptionWhenNormalAccountTryCreatePromoted() {
        //given
        var command = prepareCommand(true);
        when(userRepository.getOne(anyLong())).thenReturn(prepareUser(AccountType.NORMAL, 0));

        //when && then
        assertThrows(IllegalArgumentException.class, () -> handler.handle(command));
    }

    User prepareUser(AccountType accountType, Integer promotedAuction) {
        User user = new User();
        user.setAddress(new Address());
        user.setAccountType(accountType);
        user.setPromotedAuctionsCount(promotedAuction);
        return user;
    }

    CreateAuctionCommand prepareCommand(boolean isPromoted) {
        return new CreateAuctionCommand("test", "test",
                BigDecimal.ZERO, BigDecimal.TEN, isPromoted,
                LocalDateTime.now(), 1L, 1L);

    }
}