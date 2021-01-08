package com.sda.serwisaukcyjnybackend.application.auction;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.observation.Observation;
import com.sda.serwisaukcyjnybackend.domain.observation.ObservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class StopObservingAuctionCommandHandler implements CommandHandler<StopObservingAuctionCommand, Void> {
    private final ObservationRepository observationRepository;


    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid StopObservingAuctionCommand command) {
        Observation observation = observationRepository.getOne(command.getObservationId());

        Preconditions.checkArgument(observation.getUser().getId().equals(command.getUserId()), "Cannot stop observing not this user's observation");

        observationRepository.delete(observation);
        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return StopObservingAuctionCommand.class;
    }
}
