package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotRatePurchaseException;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.purchase.PurchaseRepository;
import com.sda.serwisaukcyjnybackend.domain.rating.Rating;
import com.sda.serwisaukcyjnybackend.domain.rating.RatingRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyerRatePurchaseCommandHandlerTest {
    @Mock
    RatingRepository ratingRepository;
    @Mock
    PurchaseRepository purchaseRepository;

    @InjectMocks
    BuyerRatePurchaseCommandHandler handler;

    @Test
    void shouldRatePurchaseByBuyerIfRatingNotExist() {
        //given
        BuyerRatePurchaseCommand command = new BuyerRatePurchaseCommand(1L, 1L, 5, "buyer's comment");
        Purchase purchase = createPurchase(1L, null);
        when(purchaseRepository.getOne(anyLong())).thenReturn(purchase);
        when(ratingRepository.save(any())).thenReturn(new Rating());

        //when & then
        handler.handle(command);
    }

    @Test
    void shouldRatePurchaseByBuyerIfRatedBySeller() {
        //given
        BuyerRatePurchaseCommand command = new BuyerRatePurchaseCommand(1L, 1L, 5, "buyer's comment");
        Rating rating = createRating(LocalDateTime.now(), null);
        Purchase purchase = createPurchase(1L, rating);
        when(purchaseRepository.getOne(anyLong())).thenReturn(purchase);
        when(ratingRepository.save(any())).thenReturn(new Rating());

        //when & then
        handler.handle(command);
    }

    @Test
    void shouldThrowExceptionIfUserIsNotBuyer() {
        //given
        BuyerRatePurchaseCommand command = new BuyerRatePurchaseCommand(1L, 2L, 5, "buyer's comment");
        Rating rating = createRating(LocalDateTime.now(), LocalDateTime.now());
        Purchase purchase = createPurchase(1L, rating);
        Long purchaseId = purchase.getId();
        when(purchaseRepository.getOne(anyLong())).thenReturn(purchase);

        //when & then
        assertThatExceptionOfType(CannotRatePurchaseException.class)
                .isThrownBy(() -> handler.handle(command))
                .withMessage("Cannot rate purchase " + purchaseId + " - user is not the buyer");
    }

    @Test
    void shouldThrowExceptionIfAlreadyRatedByBuyer() {
        //given
        BuyerRatePurchaseCommand command = new BuyerRatePurchaseCommand(1L, 1L, 5, "buyer's comment");
        Rating rating = createRating(LocalDateTime.now(), LocalDateTime.now());
        Purchase purchase = createPurchase(1L, rating);
        Long purchaseId = purchase.getId();
        when(purchaseRepository.getOne(anyLong())).thenReturn(purchase);

        //when & then
        assertThatExceptionOfType(CannotRatePurchaseException.class)
                .isThrownBy(() -> handler.handle(command))
                .withMessage("Cannot rate purchase " + purchaseId + " - already rated");
    }

    Purchase createPurchase(Long buyerId, Rating rating) {
        Purchase purchase = new Purchase();
        purchase.setId(1L);
        purchase.setBuyer(createBuyer(buyerId));
        purchase.setRating(rating);
        return purchase;
    }

    User createBuyer(Long id) {
        User buyer = new User();
        buyer.setId(id);
        return buyer;
    }

    Rating createRating(LocalDateTime sellersRatingDate, LocalDateTime buyersRatingDate) {
        Rating rating = new Rating();
        rating.setSellersRatingDate(sellersRatingDate);
        rating.setBuyersRatingDate(buyersRatingDate);
        return rating;
    }

}
