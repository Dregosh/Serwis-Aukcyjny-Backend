package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateAuctionCommand implements Command<Long> {
    @NotNull
    private String title;
    @NotNull
    private String description;
    private BigDecimal minPrice;
    private BigDecimal buyNowPrice;
    @NotNull
    private Boolean promoted;
    @NotNull
    private LocalDateTime startDate;
    @With
    private Long userId;
    @NotNull
    private Long categoryId;
}
