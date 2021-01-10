package com.sda.serwisaukcyjnybackend.view.shared.exception;

import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotBidAuctionException;
import com.sda.serwisaukcyjnybackend.application.auction.exception.CannotBuyNowException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgument(IllegalArgumentException e){
        log.error(e);
    }

    @ExceptionHandler(CannotBuyNowException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleCannotBeBought(CannotBuyNowException exception) {
        return new Error(ErrorCode.AUCTION_CANNOT_BE_BOUGHT);
    }

    @ExceptionHandler(CannotBidAuctionException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleCannotBid(CannotBidAuctionException exception) {
        return new Error(ErrorCode.AUCTION_OUTBIDDED);
    }
}
