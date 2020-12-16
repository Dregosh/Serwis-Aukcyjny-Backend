package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AuctionStatus status;
}
