package com.sda.serwisaukcyjnybackend.application.auction;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.bid.BidRepository;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.domain.purchase.PurchaseRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class FinishAuctionCommandHandler implements CommandHandler<FinishAuctionCommand, Void> {
    private final AuctionRepository auctionRepository;
    private final PurchaseRepository purchaseRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid FinishAuctionCommand command) {
        var auction = endAuctionAndGet(command.getAuctionId());
        Preconditions.checkArgument(auction.getStatus() == AuctionStatus.ENDED,
                "Auction %d not ended", auction.getId());
        if (isAnyBid(auction)) {
            createPurchase(auction);
        }
        if (auction.getIsPromoted()) {
            decrementUserPromotedAuctionsCount(auction.getSeller());
        }

        return CommandResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Auction endAuctionAndGet(Long auctionId) {
        try {
            var auction = auctionRepository.getOne(auctionId);
            auction.setStatus(AuctionStatus.ENDED);
            return auctionRepository.save(auction);
        } catch (OptimisticLockException e) {
            var auction = auctionRepository.getOne(auctionId);
            auction.setStatus(AuctionStatus.ENDED);
            return auctionRepository.save(auction);
        }
    }

    private void createPurchase(Auction auction) {
        var maxBid = bidRepository.getByAuctionAndBidPrice(auction, auction.getMaxBid());

        var purchase = new Purchase(auction, maxBid, Boolean.FALSE);
        purchaseRepository.save(purchase);
    }

    private void decrementUserPromotedAuctionsCount(User seller) {
        seller.removePromotedAuction();
        userRepository.save(seller);
    }

    private boolean isAnyBid(Auction auction) {
        return bidRepository.existsByAuction(auction);
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return FinishAuctionCommand.class;
    }
}
