package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.observation.ObservationRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class ObserveAuctionCommandHandler implements CommandHandler<ObserveAuctionCommand, Void> {
    private final ObservationRepository observationRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;


    @Override
    public CommandResult<Void> handle(@Valid ObserveAuctionCommand command) {
        Auction auction = auctionRepository.getOne(command.getAuctionId());
        User user = userRepository.getOne(command.getUserId());
        Observation observation = new Observation(auction, user);
        observationRepository.save(observation);
        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return ObserveAuctionCommand.class;
    }
}
