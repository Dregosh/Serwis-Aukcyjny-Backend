package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.UserCannotEndNotOwnedAuctionException;
import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserEndsOwnAuctionCommandHandler
        implements CommandHandler<UserEndsOwnAuctionCommand, Void> {

    private final AuctionRepository auctionRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid UserEndsOwnAuctionCommand command) {
        Auction auction = this.auctionRepository.getById(command.getAuctionId());
        if (!AuthenticatedService.getLoggedUser().getUserId().equals(auction.getSellerId())) {
            throw new UserCannotEndNotOwnedAuctionException(
                    auction.getId(), "user is not a Seller in this auction");
        }
        auction.setStatus(AuctionStatus.ENDED);
        auction.setEndDateTime(LocalDateTime.now());
        this.auctionRepository.save(auction);
        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return UserEndsOwnAuctionCommand.class;
    }
}
