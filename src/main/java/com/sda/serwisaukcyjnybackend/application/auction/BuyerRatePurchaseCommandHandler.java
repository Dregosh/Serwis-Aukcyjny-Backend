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
public class BuyerRatePurchaseCommandHandler implements CommandHandler<BuyerRatePurchaseCommand, Void> {
    private final RatingRepository ratingRepository;
    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid BuyerRatePurchaseCommand command) {
        Purchase purchase = purchaseRepository.getOne(command.getPurchaseId());

        checkIfEligibleForRatingByBuyer(purchase, command.getUserId());

        Rating rating = getRatingOrCreateIfNotExist(purchase);

        rating.setBuyersRating(command.getBuyersRating());
        rating.setBuyersComment(command.getBuyersComment());
        rating.setBuyersRatingDate(LocalDateTime.now());

        ratingRepository.save(rating);
        return CommandResult.ok();
    }

    private void checkIfEligibleForRatingByBuyer(Purchase purchase, Long userId) {
        checkIfUserIsBuyer(purchase, userId);
        if (isRatingNotNull(purchase)) {
            checkIfAlreadyRatedByBuyer(purchase);
        }
    }

    private void checkIfUserIsBuyer(Purchase purchase, Long userId) {
        if (!purchase.getBuyerId().equals(userId)) {
            throw new CannotRatePurchaseException(purchase.getId(), "user is not the buyer");
        }
    }

    private void checkIfAlreadyRatedByBuyer(Purchase purchase) {
        if (nonNull(purchase.getRating().getBuyersRatingDate())) {
            throw new CannotRatePurchaseException(purchase.getId(), "already rated");
        }
    }

    private boolean isRatingNotNull(Purchase purchase) {
        return nonNull(purchase.getRating());
    }

    private Rating getRatingOrCreateIfNotExist(Purchase purchase) {
        Rating rating;
        if(isRatingNotNull(purchase)) {
            rating = purchase.getRating();
        } else {
            rating = new Rating(purchase);
        }
        return rating;
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return BuyerRatePurchaseCommand.class;
    }
}
