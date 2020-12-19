package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.application.auction.BidAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.auction.AddPhotosToAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.auction.BuyNowCommand;
import com.sda.serwisaukcyjnybackend.application.auction.CreateAuctionCommand;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.config.auth.security.SAUserDetails;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService.getLoggedUser;

@RestController
@RequestMapping("api/auctions")
@RequiredArgsConstructor
public class AuctionController {
    private final CommandDispatcher commandDispatcher;
    private final AuctionService auctionService;

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

    @PostMapping("/{auctionId}/buy-now")
    public void buyNowAuction(@PathVariable(name = "auctionId") Long auctionId,
                              @AuthenticationPrincipal SAUserDetails userDetails) {
        commandDispatcher.handle(new BuyNowCommand(auctionId, userDetails.getUserId()));
    }

    @GetMapping("/byCategory/{categoryId}")
    public Page<SimpleAuctionDTO> getByCategory(@PathVariable("categoryId") Long categoryId,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam(value = "sort", required = false, defaultValue = "ID_DESC") AuctionSort sort,
                                                @RequestParam Map<AuctionFilter, ?> filterMap) {
        return auctionService.getSortedAuctionByCategory(categoryId, page, size, sort, filterMap);
    }

    @GetMapping("/{id}")
    public AuctionDTO getById(@PathVariable("id") Long id) {
        return auctionService.getAuctionById(id);
    }

    @GetMapping("/bidded")
    public List<SimpleAuctionDTO> getBidded() {
        return auctionService.getBidded(getLoggedUser().getUserId());
    }

    @GetMapping("/observed")
    public List<SimpleAuctionDTO> getObserved() {
        return auctionService.getObserved(getLoggedUser().getUserId());
    }

    @GetMapping("/own")
    public List<SimpleAuctionDTO> getUserAuction() {
        return auctionService.getUserAuction(getLoggedUser().getUserId());
    }

}
