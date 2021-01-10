package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotRatePurchaseException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.purchase.PurchaseRepository;
import com.sda.serwisaukcyjnybackend.domain.rating.Rating;
import com.sda.serwisaukcyjnybackend.domain.rating.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class SellerRatePurchaseCommandHandler
        implements CommandHandler<SellerRatePurchaseCommand, Void> {
    private final RatingRepository ratingRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid SellerRatePurchaseCommand command) {
        Purchase purchase = this.purchaseRepository.findById(command.getPurchaseId()).orElseThrow();
        checkIfEligibleForRatingBySeller(purchase, command.getUserId());

        Rating rating = getRatingOrCreateIfNotExist(purchase);

        rating.setSellersRating(command.getSellersRating());
        rating.setSellersComment(command.getSellersComment());
        rating.setSellersRatingDate(LocalDateTime.now());

        this.ratingRepository.save(rating);
        return CommandResult.ok();
    }

    private void checkIfEligibleForRatingBySeller(Purchase purchase, Long userId) {
        checkIfUserIsSeller(purchase, userId);
        if (isRatingNotNull(purchase)) {
            checkIfAlreadyRatedBySeller(purchase);
        }
    }

    private void checkIfUserIsSeller(Purchase purchase, Long userId) {
        if (!purchase.getAuction().getSeller().getId().equals(userId)) {
            throw new CannotRatePurchaseException(purchase.getId(), "user is not the seller");
        }
    }

    private void checkIfAlreadyRatedBySeller(Purchase purchase) {
        if (nonNull(purchase.getRating().getSellersRatingDate())) {
            throw new CannotRatePurchaseException(purchase.getId(), "already rated");
        }
    }

    private boolean isRatingNotNull(Purchase purchase) {
        return nonNull(purchase.getRating());
    }

    private Rating getRatingOrCreateIfNotExist(Purchase purchase) {
        Rating rating;
        if (isRatingNotNull(purchase)) {
            rating = purchase.getRating();
        } else {
            rating = new Rating(purchase);
        }
        return rating;
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return SellerRatePurchaseCommand.class;
    }
}
