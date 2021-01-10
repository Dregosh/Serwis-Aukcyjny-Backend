package com.sda.serwisaukcyjnybackend.application.auction;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotBidAuctionException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.bid.Bid;
import com.sda.serwisaukcyjnybackend.domain.bid.BidRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BidAuctionCommandHandler implements CommandHandler<BidAuctionCommand, Void> {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid BidAuctionCommand command) {
        var auction = auctionRepository.findById(command.getAuctionId())
                .orElseThrow();
        var user = userRepository.getOne(command.getUserId());
        Preconditions.checkArgument(!auction.getSellerId().equals(user.getId()),
                "User cannot bid his own auction");

        tryBidAuction(auction, user, command.getBidPrice());

        return CommandResult.ok();
    }

    private void tryBidAuction(Auction auction, User user, BigDecimal bidPrice) {
        var bid = new Bid(auction, user, bidPrice);
        try {
            setMaxBid(auction, bid, bidPrice);
        } catch (OptimisticLockException e) {
            var updatedAuction = auctionRepository.findById(auction.getId())
                    .orElseThrow();
            setMaxBid(updatedAuction, bid, bidPrice);
        }
    }

    private void setMaxBid(Auction auction, Bid bid, BigDecimal bidPrice) {
        if (auction.canBeBidded() && isBidBigger(bidPrice, auction.getMaxBid())) {
            auction.setMaxBid(bidPrice);
            auctionRepository.save(auction);
            bidRepository.save(bid);
        } else {
            throw new CannotBidAuctionException(auction.getId());
        }
    }

    private boolean isBidBigger(BigDecimal newBid, BigDecimal currentBid) {
        return newBid.compareTo(currentBid) > 0;
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return BidAuctionCommand.class;
    }
}
