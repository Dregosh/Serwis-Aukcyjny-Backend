package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {
    @Mock
    AuctionRepository auctionRepository;

    @InjectMocks
    DashboardService dashboardService;

    @Test
    void shouldGetEmptyList() {
        //given
        when(this.auctionRepository.findAllByStatusNotOrderByStartDateTimeDesc(
                AuctionStatus.ENDED, PageRequest.of(0, 10)
        )).thenReturn(new ArrayList<>());
        //when
        List<SimpleAuctionDTO> result =
                this.dashboardService.getActiveAuctionsOrderByLatestCreated();
        //then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldGetSimpleAuctionDTO() {
        //given
        Auction sampleAuction = new Auction();
        sampleAuction.setId(1L);
        sampleAuction.setTitle("sampleAuctionTitle");
        sampleAuction.setBids(new ArrayList<>());
        sampleAuction.setMinPrice(BigDecimal.valueOf(100));
        sampleAuction.setBuyNowPrice(BigDecimal.valueOf(200));

        List<Auction> auctions = new ArrayList<>();
        auctions.add(sampleAuction);

        when(this.auctionRepository.findAllByStatusNotOrderByStartDateTimeDesc(
                AuctionStatus.ENDED, PageRequest.of(0, 10)
        )).thenReturn(auctions);
        //when
        List<SimpleAuctionDTO> result =
                this.dashboardService.getActiveAuctionsOrderByLatestCreated();
        //then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

}
