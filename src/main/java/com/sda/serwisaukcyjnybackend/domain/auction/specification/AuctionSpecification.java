package com.sda.serwisaukcyjnybackend.domain.auction.specification;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionStatus;
import com.sda.serwisaukcyjnybackend.view.auction.AuctionFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Map;

@Builder
public class AuctionSpecification implements Specification<Auction> {
    private static final String MAX_BID_PRICE = "maxBid";
    private static final String BUY_NOW_PRICE = "buyNowPrice";
    private static final String CATEGORY = "category";
    private static final String CATEGORY_ID = "id";
    private static final String PROMOTED = "isPromoted";
    private static final String STATUS = "status";
    private static final String ALL_STATUSES = "allStatuses";

    private BigDecimal buyNowPriceFrom;
    private BigDecimal buyNowPriceTo;
    private BigDecimal bidPriceFrom;
    private BigDecimal bidPriceTo;
    private boolean onlyPromoted;
    private boolean onlyBuyNow;
    private boolean onlyCanBid;
    private boolean allStatuses;
    private Long categoryId;


    @Override
    public Predicate toPredicate(Root<Auction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(buyNowPriceFrom(root, criteriaBuilder), buyNowPriceTo(root, criteriaBuilder),
                bidPriceFrom(root, criteriaBuilder), bidPriceTo(root, criteriaBuilder), onlyPromoted(root, criteriaBuilder),
                onlyBuyNow(root, criteriaBuilder), byCategory(root, criteriaBuilder), onlyBid(root, criteriaBuilder),
                notEnded(root, criteriaBuilder));
    }

    private Predicate buyNowPriceFrom(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (buyNowPriceFrom == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.greaterThanOrEqualTo(root.get(BUY_NOW_PRICE), buyNowPriceFrom);
    }

    private Predicate buyNowPriceTo(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (buyNowPriceTo == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.lessThanOrEqualTo(root.get(BUY_NOW_PRICE), buyNowPriceTo);
    }

    private Predicate bidPriceFrom(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (bidPriceFrom == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.greaterThanOrEqualTo(root.get(MAX_BID_PRICE), bidPriceFrom);
    }

    private Predicate bidPriceTo(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (bidPriceTo == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.lessThanOrEqualTo(root.get(MAX_BID_PRICE), bidPriceTo);
    }

    private Predicate onlyPromoted(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (onlyPromoted) {
            return criteriaBuilder.isTrue(root.get(PROMOTED));
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Predicate onlyBuyNow(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (onlyBuyNow) {
            return criteriaBuilder.equal(root.get(STATUS), AuctionStatus.CREATED);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Predicate onlyBid(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (onlyCanBid) {
            return criteriaBuilder.equal(root.get(STATUS), AuctionStatus.STARTED);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    private Predicate byCategory(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (categoryId == null) {
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        return criteriaBuilder.equal(root.get(CATEGORY).get(CATEGORY_ID), categoryId);
    }
    private Predicate notEnded(Root<Auction> root, CriteriaBuilder criteriaBuilder) {
        if (!allStatuses) {
            return criteriaBuilder.notEqual(root.get(STATUS), AuctionStatus.ENDED);
        }
        return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    public AuctionSpecification(BigDecimal buyNowPriceFrom, BigDecimal buyNowPriceTo,
                                BigDecimal bidPriceFrom, BigDecimal bidPriceTo,
                                boolean onlyPromoted, boolean onlyBuyNow,
                                boolean onlyCanBid, boolean allStatuses, Long categoryId) {
        Preconditions.checkArgument(!(onlyBuyNow && onlyCanBid), "Could not filter by onlyBuyNow and onlyBid");
        this.buyNowPriceFrom = buyNowPriceFrom;
        this.buyNowPriceTo = buyNowPriceTo;
        this.bidPriceFrom = bidPriceFrom;
        this.bidPriceTo = bidPriceTo;
        this.onlyPromoted = onlyPromoted;
        this.onlyBuyNow = onlyBuyNow;
        this.onlyCanBid = onlyCanBid;
        this.allStatuses = allStatuses;
        this.categoryId = categoryId;
    }

    public static AuctionSpecificationBuilder getFromAuctionFilterMap(Map<String, String> stringMap) {
        var filterMap = AuctionFilter.mapToFilterAuctionMap(stringMap);
        var builder = builder();
        if (filterMap.containsKey(AuctionFilter.BID_PRICE_TO)) {
            builder.bidPriceTo(new BigDecimal(filterMap.get(AuctionFilter.BID_PRICE_TO)));
        }
        if (filterMap.containsKey(AuctionFilter.BID_PRICE_FROM)) {
            builder.bidPriceFrom(new BigDecimal(filterMap.get(AuctionFilter.BID_PRICE_FROM)));
        }
        if (filterMap.containsKey(AuctionFilter.BUY_NOW_PRICE_TO)) {
            builder.buyNowPriceTo(new BigDecimal(filterMap.get(AuctionFilter.BUY_NOW_PRICE_TO)));
        }
        if (filterMap.containsKey(AuctionFilter.BUY_NOW_PRICE_FROM)) {
            builder.buyNowPriceFrom(new BigDecimal(filterMap.get(AuctionFilter.BUY_NOW_PRICE_FROM)));
        }
        if (filterMap.containsKey(AuctionFilter.ONLY_PROMOTED)) {
            builder.onlyPromoted(true);
        }
        if (filterMap.containsKey(AuctionFilter.ONLY_BUY_NOW)) {
            builder.onlyBuyNow(true);
        }
        if (filterMap.containsKey(AuctionFilter.ONLY_CAN_BID)) {
            builder.onlyCanBid(true);
        }
        if (filterMap.containsKey(AuctionFilter.ALL_STATUSES)) {
            builder.allStatuses(true);
        }
        return builder;
    }
}
