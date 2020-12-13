package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    AuctionRepository auctionRepository;

    @InjectMocks
    DashboardService dashboardService;

    List<Auction> auctionList = new ArrayList<>();

    void prepareAllAuctionsBase() {
        Auction auction1 = new Auction();
        auction1.setId(1L);
        auction1.setVersion(null);
        auction1.setTitle("komp");
        auction1.setDescription("");
        auction1.setMinPrice(BigDecimal.valueOf(100.0));
        auction1.setBuyNowPrice(BigDecimal.valueOf(200.0));
        auction1.setIsPromoted(false);
        auction1.setLocation(
                new Address("Wawa", "mazowieckie", "lotna", "27", "05-500 "));
        auction1.setStartDateTime(LocalDateTime.of(2020, 12, 13, 11, 49));
        auction1.setEndDateTime(LocalDateTime.of(2020, 12, 20, 11, 49));
        auction1.setStatus(AuctionStatus.STARTED);
        auction1.setSeller(null);

        Auction auction2 = new Auction();
        auction2.setId(2L);
        auction2.setVersion(null);
        auction2.setTitle("tv");
        auction2.setDescription("");
        auction2.setMinPrice(BigDecimal.valueOf(200.0));
        auction2.setBuyNowPrice(BigDecimal.valueOf(500.0));
        auction2.setIsPromoted(false);
        auction2.setLocation(
                new Address("Wawa", "mazowieckie", "lotna", "27", "05-500"));
        auction2.setStartDateTime(LocalDateTime.of(2020, 12, 16, 11, 49));
        auction2.setEndDateTime(LocalDateTime.of(2020, 12, 18, 11, 49));
        auction2.setStatus(AuctionStatus.STARTED);
        auction2.setSeller(null);

        Auction auction3 = new Auction();
        auction3.setId(3L);
        auction3.setVersion(null);
        auction3.setTitle("radio");
        auction3.setDescription("");
        auction3.setMinPrice(BigDecimal.valueOf(50.0));
        auction3.setBuyNowPrice(BigDecimal.valueOf(100.0));
        auction3.setIsPromoted(false);
        auction3.setLocation(
                new Address("Wawa", "mazowieckie", "lotna", "27", "05-500"));
        auction3.setStartDateTime(LocalDateTime.of(2020, 12, 10, 11, 49));
        auction3.setEndDateTime(LocalDateTime.of(2020, 12, 12, 11, 49));
        auction3.setStatus(AuctionStatus.ENDED);
        auction3.setSeller(null);

        auctionList.add(auction1);
        auctionList.add(auction2);
        auctionList.add(auction3);
    }

}
