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
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        dashboardService.limit = 10;
        when(this.auctionRepository.findAllByStatusNotOrderByStartDateTimeDesc(
                any(), any()
        )).thenReturn(new ArrayList<>());
        //when
        List<SimpleAuctionDTO> result =
                this.dashboardService.getActiveAuctionsOrderByLatestCreated();
        //then
        assertThat(result).isEmpty();
    }
}
