package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    AuctionRepository auctionRepository;

    @InjectMocks
    DashboardService dashboardService;

//    @BeforeAll
    void prepareAllAuctionsBase() {

        /*Auction auction = new Auction();
        auction.setId(1L);
        auction.setVersion(null);
        auction.setTitle("komp");
        auction.setDescription("");
        auction.setMinPrice(BigDecimal.valueOf(100.0));
        auction.setBuyNowPrice(BigDecimal.valueOf(200.0));
        auction.setIsPromoted(false);

        auction.setLocation();  // ??

        auction.setStartDateTime(LocalDateTime.of(2020, 12, 13, 11, 49));
        auction.setEndDateTime(LocalDateTime.of(2020, 12, 20, 11, 49));
        auction.setStatus(AuctionStatus.STARTED);
        auction.setSeller(null);*/
    }

}
