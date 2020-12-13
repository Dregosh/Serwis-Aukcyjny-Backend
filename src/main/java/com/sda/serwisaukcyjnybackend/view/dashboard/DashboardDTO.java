package com.sda.serwisaukcyjnybackend.view.dashboard;

import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardDTO implements Serializable {
    private List<SimpleAuctionDTO> lastAdded = new ArrayList<>();
    private List<SimpleAuctionDTO> nearlyEnd = new ArrayList<>();
    private List<SimpleAuctionDTO> loggedUserAuctions = new ArrayList<>();
    private List<SimpleAuctionDTO> biddedAuctions = new ArrayList<>();
    private List<SimpleAuctionDTO> observedAuctions = new ArrayList<>();
    private List<SimpleAuctionDTO> justFinished = new ArrayList<>();
}
