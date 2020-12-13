package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.application.auction.CreateAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.config.auth.security.SAUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final CommandDispatcher commandDispatcher;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAuction(@RequestBody CreateAuctionCommand createAuctionCommand,
                              @AuthenticationPrincipal SAUserDetails userDetails) {
        commandDispatcher.handle(createAuctionCommand.withUserId(userDetails.getUserId()));
    }
}
