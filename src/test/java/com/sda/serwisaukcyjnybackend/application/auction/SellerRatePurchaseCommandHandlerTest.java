package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotRatePurchaseException;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerRatePurchaseCommandHandlerTest {
    @Mock
    RatingRepository ratingRepository;
    @Mock
    PurchaseRepository purchaseRepository;

    @InjectMocks
    SellerRatePurchaseCommandHandler handler;

    @Test
    void ShouldRatePurchaseBySellerIfRatingNotExist() {
        //given
        SellerRatePurchaseCommand command = new SellerRatePurchaseCommand(1L, 1L, 5, "seller's comment");
        Purchase purchase = createPurchase(1L, null);
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));
        when(ratingRepository.save(any())).thenReturn(new Rating());
        //when&then
        handler.handle(command);
    }

    @Test
    void shouldRatePurchaseBySellerIfRatedByBuyer() {
        //given
        SellerRatePurchaseCommand command = new SellerRatePurchaseCommand(1L, 1L, 5, "seller's comment");
        Rating rating = createRating(LocalDateTime.of(2021, 1, 1, 12, 0), null);
        Purchase purchase = createPurchase(1L, rating);
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));
        when(ratingRepository.save(any())).thenReturn(new Rating());
        //when&then
        handler.handle(command);
    }

    @Test
    void shouldThrowExceptionIfUserIsNotSeller() {
        //given
        SellerRatePurchaseCommand command = new SellerRatePurchaseCommand(1L, 2L, 5, "seller's comment");
        Rating rating =
                createRating(LocalDateTime.of(2021, 1, 1, 12, 0), LocalDateTime.of(2021, 1, 1, 12, 0));
        Purchase purchase = createPurchase(1L, rating);
        Long purchaseId = purchase.getId();
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));
        //when&then
        assertThatExceptionOfType(CannotRatePurchaseException.class)
                .isThrownBy(() -> handler.handle(command))
                .withMessage("Cannot rate purchase " + purchaseId + " - user is not the seller");
    }

    @Test
    void shouldThrowExceptionIfAlreadyRatedBySeller() {
        //given
        SellerRatePurchaseCommand command = new SellerRatePurchaseCommand(1L, 1L, 5, "seller's comment");
        Rating rating =
                createRating(LocalDateTime.of(2021, 1, 1, 12, 0), LocalDateTime.of(2021, 1, 1, 12, 0));
        Purchase purchase = createPurchase(1L, rating);
        Long purchaseId = purchase.getId();
        when(purchaseRepository.findById(anyLong())).thenReturn(Optional.of(purchase));
        //when&then
        assertThatExceptionOfType(CannotRatePurchaseException.class)
                .isThrownBy(() -> handler.handle(command))
                .withMessage("Cannot rate purchase " + purchaseId + " - already rated");
    }

    Purchase createPurchase(Long sellerId, Rating rating) {
        Purchase purchase = new Purchase();
        Auction auction = new Auction();
        auction.setSeller(createSeller(sellerId));
        purchase.setAuction(auction);
        purchase.setId(1L);
        purchase.setRating(rating);
        return purchase;
    }

    User createSeller(Long id) {
        User seller = new User();
        seller.setId(id);
        return seller;
    }

    Rating createRating(LocalDateTime buyersRatingDate, LocalDateTime sellersRatingDate) {
        Rating rating = new Rating();
        rating.setBuyersRatingDate(buyersRatingDate);
        rating.setSellersRatingDate(sellersRatingDate);
        return rating;
    }
}
