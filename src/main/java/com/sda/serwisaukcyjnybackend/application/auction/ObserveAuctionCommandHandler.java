package com.sda.serwisaukcyjnybackend.application.auction;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotObserveAuctionException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.observation.ObservationRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class ObserveAuctionCommandHandler implements CommandHandler<ObserveAuctionCommand, Void> {
    private final ObservationRepository observationRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid ObserveAuctionCommand command) {
        Auction auction = auctionRepository.getOne(command.getAuctionId());
        User user = userRepository.getOne(command.getUserId());

        checkIfAuctionEnded(auction);
        Preconditions.checkArgument(!auction.getSellerId().equals(user.getId()),
                "Cannot observe own auction");

        Observation observation = new Observation(auction, user);
        observationRepository.save(observation);
        return CommandResult.ok();
    }

    private void checkIfAuctionEnded(Auction auction) {
        if (auction.getStatus().equals(AuctionStatus.ENDED)) {
            throw new CannotObserveAuctionException(auction.getId());
        }
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return ObserveAuctionCommand.class;
    }
}
