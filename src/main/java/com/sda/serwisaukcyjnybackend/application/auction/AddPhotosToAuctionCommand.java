package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class AddPhotosToAuctionCommand implements Command<Void> {
    Long auctionId;
    MultipartFile[] photos;
}
