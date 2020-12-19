package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.application.auction.BidAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.auction.AddPhotosToAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.auction.CreateAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.config.auth.security.SAUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService.getLoggedUser;

@RestController
@RequestMapping("api/auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final CommandDispatcher commandDispatcher;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createAuction(@RequestBody @Valid CreateAuctionCommand createAuctionCommand) {
        return commandDispatcher.handle(createAuctionCommand.withUserId(getLoggedUser().getUserId())).getPayload();
    }

    @PostMapping("/bid")
    public void bidAuction(@RequestBody @Valid BidAuctionCommand bidAuctionCommand) {
        commandDispatcher.handle(bidAuctionCommand.withUserId(getLoggedUser().getUserId()));
    }

    @PostMapping("/{auctionId}/images")
    public void addPhotoToAuction(@PathVariable(name = "auctionId") Long auctionId,
                                  @RequestParam MultipartFile[] files) {
        commandDispatcher.handle(new AddPhotosToAuctionCommand(auctionId, files));
    }
}
