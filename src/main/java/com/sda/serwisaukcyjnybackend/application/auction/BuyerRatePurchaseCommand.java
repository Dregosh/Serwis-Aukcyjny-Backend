package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BuyerRatePurchaseCommand implements Command<Void> {
    @NotNull
    Long purchaseId;
    @With
    Long userId;
    Integer buyersRating;
    String buyersComment;
}
