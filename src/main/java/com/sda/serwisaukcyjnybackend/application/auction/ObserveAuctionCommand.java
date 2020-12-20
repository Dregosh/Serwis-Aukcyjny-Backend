package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class ObserveAuctionCommand implements Command<Void> {
    @NotNull
    Long auctionId;
    @NotNull
    Long userId;
}
