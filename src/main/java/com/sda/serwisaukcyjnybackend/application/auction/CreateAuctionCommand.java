package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAuctionCommand implements Command<Void> {
    private String title;
    private String description;
    private BigDecimal minPrice;
    private BigDecimal buyNowPrice;
    private Boolean promoted;
    private LocalDateTime startDate;
    @With
    private Long userId;
}
