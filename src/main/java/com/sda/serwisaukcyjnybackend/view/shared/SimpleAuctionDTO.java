package com.sda.serwisaukcyjnybackend.view.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SimpleAuctionDTO {
    private Long id;
    private String title;
    private String mainPhotoName;
    private BigDecimal bidPrice;
    private BigDecimal buyNowPrice;
    private Boolean alreadyBidded;
    private Integer biddersAmount;
    private AuctionStatus status;
    private Boolean isBought;
    private Long purchaseId;
    private boolean promoted;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDateTime;
}
