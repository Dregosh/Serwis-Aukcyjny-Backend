package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

@Value
public class FinishAuctionCommand implements Command<Void> {
    Long auctionId;
    Boolean isBuyNow;
}
