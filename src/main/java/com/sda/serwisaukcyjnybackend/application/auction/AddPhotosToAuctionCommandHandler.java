package com.sda.serwisaukcyjnybackend.application.auction;

import com.sda.serwisaukcyjnybackend.application.auction.exception.AuctionNotFoundException;
import com.sda.serwisaukcyjnybackend.application.auction.exception.FileStorageException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.config.app.photo.PhotoProperties;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.auction.Photo;
import com.sda.serwisaukcyjnybackend.domain.auction.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AddPhotosToAuctionCommandHandler implements CommandHandler<AddPhotosToAuctionCommand, Void> {
    private final PhotoProperties photoProperties;
    private final PhotoRepository photoRepository;
    private final AuctionRepository auctionRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid AddPhotosToAuctionCommand command) {
        var auction = auctionRepository.findAuctionByIdAndSellerId(command.getAuctionId(), command.getSellerId())
                .orElseThrow(() -> new AuctionNotFoundException(command.getAuctionId(), command.getSellerId()));
        var photo = savePhotoInStorage(auction, command.getImage());
        photoRepository.save(photo);

        return CommandResult.ok();
    }

    private Photo savePhotoInStorage(Auction auction, MultipartFile photo) {
        try {
            String photoName = UUID.randomUUID().toString()
                    + StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename()));
            Path copyLocation = Paths.get(photoProperties.getDestination() + File.separator + photoName);
            Files.copy(photo.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            return new Photo(photoName, auction);
        } catch (IOException e) {
            throw new FileStorageException();
        }
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return AddPhotosToAuctionCommand.class;
    }
}
