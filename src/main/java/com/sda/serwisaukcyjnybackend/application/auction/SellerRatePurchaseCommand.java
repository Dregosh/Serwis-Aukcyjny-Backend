package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerRatePurchaseCommand implements Command<Void> {
    @NotNull
    private Long purchaseId;
    @With
    private Long userId;
    @NotNull
    private Integer sellersRating;
    private String sellersComment;
}
