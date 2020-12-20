package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;
import lombok.With;

import javax.validation.constraints.NotNull;

@Value
public class StopObservingAuctionCommand implements Command<Void> {
    @NotNull
    Long observationId;
    @With
    Long userId;
}
