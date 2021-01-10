package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.application.auction.*;
import com.sda.serwisaukcyjnybackend.application.auction.exception.AuctionNotFoundException;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.view.auth.CreateAuctionUserDTO;
import com.sda.serwisaukcyjnybackend.view.auth.UserService;
import com.sda.serwisaukcyjnybackend.view.shared.SimpleAuctionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;

    @ExceptionHandler(AuctionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }

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
                                  @RequestParam MultipartFile file) {
        commandDispatcher.handle(new AddPhotosToAuctionCommand(auctionId, getLoggedUser().getUserId(), file));
    }

    @PostMapping("/{auctionId}/buy-now")
    public void buyNowAuction(@PathVariable(name = "auctionId") Long auctionId) {
        commandDispatcher.handle(new BuyNowCommand(auctionId, getLoggedUser().getUserId()));
    }

    @PostMapping("/{auctionId}/observe")
    public void observeAuction(@PathVariable(name = "auctionId") Long auctionId) {
        commandDispatcher.handle(new ObserveAuctionCommand(auctionId, getLoggedUser().getUserId()));
    }

    @PostMapping("/{auctionId}/stop-observing")
    public void stopObservingAuction(@PathVariable(name = "auctionId") Long auctionId) {
        commandDispatcher.handle(new StopObservingAuctionCommand(auctionId, getLoggedUser().getUserId()));
    }

    @PostMapping("/rate-buyer")
    public void rateByBuyer(@RequestBody @Valid BuyerRatePurchaseCommand buyerRatePurchaseCommand) {
        commandDispatcher.handle(buyerRatePurchaseCommand.withUserId(getLoggedUser().getUserId()));
    }

    @PostMapping("/rate-seller")
    public void rateBySeller(@RequestBody @Valid SellerRatePurchaseCommand sellerRatePurchaseCommand) {
        commandDispatcher.handle(sellerRatePurchaseCommand.withUserId(getLoggedUser().getUserId()));
    }

    @GetMapping("/byCategory/{categoryId}")
    public Page<SimpleAuctionDTO> getByCategory(@PathVariable("categoryId") Long categoryId,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam(value = "sort", required = false, defaultValue = "ID_DESC") AuctionSort sort,
                                                @RequestParam Map<String, String> filterMap) {
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

    @GetMapping("/own-sorted")
    public Page<SimpleAuctionDTO> getUserAuctionsSorted(
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "ID_DESC") AuctionSort sort,
            @RequestParam Map<String, String> filterMap) {
        return auctionService.getUserAuctionsSorted(
                getLoggedUser().getUserId(), page, size, sort, filterMap);
    }

    @GetMapping("/create-auction-data")
    public CreateAuctionUserDTO getCreateAuctionUser() {
        return userService.getCreateAuctionUserDTO(getLoggedUser().getUserId());
    }

    @PostMapping("/end-own-auction")
    public void UserEndsHisOwnAuction(@RequestBody @Valid UserEndsOwnAuctionCommand command) {
        commandDispatcher.handle(command);
    }
}
