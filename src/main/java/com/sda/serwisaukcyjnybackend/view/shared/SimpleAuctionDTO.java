package com.sda.serwisaukcyjnybackend.view.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
}
