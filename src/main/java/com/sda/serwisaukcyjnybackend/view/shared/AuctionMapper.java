package com.sda.serwisaukcyjnybackend.view.shared;

import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.domain.auction.Photo;
import com.sda.serwisaukcyjnybackend.domain.purchase.Purchase;
import com.sda.serwisaukcyjnybackend.view.auction.AuctionDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService.getLoggedUserInfo;
import static java.util.Objects.nonNull;

public class AuctionMapper {

    public static SimpleAuctionDTO mapToSimpleAuction(Auction auction) {
        return SimpleAuctionDTO
                .builder()
                .id(auction.getId())
                .title(auction.getTitle())
                .bidPrice(auction.getMaxBid())
                .buyNowPrice(auction.getBuyNowPrice())
                .alreadyBidded(auction.getBids().size() > 0)
                .biddersAmount(auction.getBids().size())
                .mainPhotoName(auction.getPhotos().size() != 0 ?
                               auction.getPhotos().get(0).getName() : null)
                .status(auction.getStatus())
                .isBought(auction.isBought())
                .purchaseId(auction.getPurchase() != null ? auction.getPurchase().getId() : null)
                .alreadyRatedBySeller(checkIfAlreadyRatedBySeller(auction.getPurchase()))
                .startDateTime(auction.getStartDateTime())
                .endDateTime(auction.getEndDateTime())
                .build();
    }

    public static AuctionDTO map(Auction auction, boolean observed) {
        return AuctionDTO.builder()
                         .buyNowPrice(auction.getBuyNowPrice())
                         .canBid(auction.getStatus() == AuctionStatus.STARTED &&
                                 !isUserAuction(auction.getSellerId()))
                         .canBuyNow(auction.getStatus() == AuctionStatus.CREATED &&
                                    !isUserAuction(auction.getSellerId()))
                         .description(auction.getDescription())
                         .endDateTime(auction.getEndDateTime())
                         .id(auction.getId())
                         .maxBid(auction.getMaxBid())
                         .observed(observed)
                         .premium(auction.getIsPromoted())
                         .sellerDisplayName(auction.getSellerDisplayName())
                         .sellerId(auction.getSellerId())
                         .startDateTime(auction.getStartDateTime())
                         .status(auction.getStatus())
                         .title(auction.getTitle())
                         .photoNames(filterPhotos(auction))
                         .userAuction(isUserAuction(auction.getSellerId()))
                         .build();
    }

    private static List<String> filterPhotos(Auction auction) {
        return auction.getPhotos().stream()
                      .map(Photo::getName)
                      .collect(Collectors.toList());
    }

    private static boolean isUserAuction(Long sellerId) {
        return getLoggedUserInfo()
                .map(userDetails -> userDetails.getUserId().equals(sellerId))
                .orElse(false);
    }

    private static boolean checkIfAlreadyRatedBySeller(Purchase purchase) {
        if (nonNull(purchase)) {
            if (nonNull(purchase.getRating())) {
                return purchase.getRating().getSellersRating() != null;
            }
        }
        return false;
    }
}
