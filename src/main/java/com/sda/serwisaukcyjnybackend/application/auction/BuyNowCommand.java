package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

@Value
public class BuyNowCommand implements Command<Void> {
    Long auctionId;
    Long userId;
}
