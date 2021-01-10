package com.sda.serwisaukcyjnybackend.application.auction;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotBuyNowException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.purchase.PurchaseRepository;
import com.sda.serwisaukcyjnybackend.domain.purchase.event.PurchaseCreated;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class BuyNowCommandHandler implements CommandHandler<BuyNowCommand, Void> {
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid BuyNowCommand command) {
        var user = userRepository.findById(command.getUserId()).orElseThrow();
        var auction = auctionRepository.getById(command.getAuctionId());

        Preconditions.checkArgument(!auction.getSellerId().equals(user.getId()), "User cannot buy own auction");

        var purchase = tryPurchaseAuction(user, auction);
        eventPublisher.publishEvent(new PurchaseCreated(purchase.getId(),
                auction.getSeller().getEmail(), user.getEmail(),
                auction.getSeller().getDisplayName(), user.getDisplayName(),
                auction.getId(), auction.getTitle(), purchase.getPrice(),
                purchase.getIsBuyNow()));
        return CommandResult.ok();
    }

    private Purchase tryPurchaseAuction(User user, Auction auction) {
        var purchase = new Purchase(user, auction, auction.getBuyNowPrice(), Boolean.TRUE);

        try {
            return buyNow(auction, purchase);
        } catch (OptimisticLockException e) {
            throw new CannotBuyNowException(auction.getId());
        }
    }

    private Purchase buyNow(Auction auction, Purchase purchase) {
        if (auction.canBeBoughtNow()) {
            auction.markAsEnded();
            auctionRepository.save(auction);
            return purchaseRepository.save(purchase);
        } else {
            throw new CannotBuyNowException(auction.getId());
        }

    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return BuyNowCommand.class;
    }
}
