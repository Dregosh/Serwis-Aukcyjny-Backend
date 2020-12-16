package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BidAuctionCommand implements Command<Void> {
    @NotNull
    private Long auctionId;
    @NotNull
    private BigDecimal bidPrice;
    @With
    private Long userId;
}
