package com.sda.serwisaukcyjnybackend.view.auction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuctionDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Long sellerId;
    private String sellerDisplayName;
    private BigDecimal buyNowPrice;
    private BigDecimal maxBid;
    private boolean canBuyNow;
    private boolean canBid;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDateTime;

    private AuctionStatus status;
    private boolean observed;
    private boolean premium;
    private boolean userAuction;
    private List<String> photoNames;
}
