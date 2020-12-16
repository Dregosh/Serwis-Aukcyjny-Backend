package com.sda.serwisaukcyjnybackend.application.auction;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.auction.Auction;
import com.sda.serwisaukcyjnybackend.domain.auction.AuctionRepository;
import com.sda.serwisaukcyjnybackend.domain.category.CategoryRepository;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class CreateAuctionCommandHandler implements CommandHandler<CreateAuctionCommand, Long> {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;

    @Value("${app.auction.maxPromoted}")
    protected int maxPromoted;
    @Value("${app.auction.duration}")
    protected int duration;

    @Override
    @Transactional
    public CommandResult<Long> handle(@Valid CreateAuctionCommand command) {
        var user = userRepository.getOne(command.getUserId());
        validatePromotedAuction(command, user);
        var category = categoryRepository.getOne(command.getCategoryId());

        var auction = new Auction(user, command.getTitle(),
                command.getDescription(), command.getMinPrice(),
                command.getBuyNowPrice(), command.getPromoted(),
                command.getStartDate(), command.getStartDate().plusDays(duration),
                category);

        if (auction.getIsPromoted()) {
            user.addPromotedAuction();
            userRepository.save(user);
        }
        var auctionId = auctionRepository.save(auction).getId();

        return CommandResult.created(auctionId);
    }

    private void validatePromotedAuction(CreateAuctionCommand command, User user) {
        if (command.getPromoted()) {
            Preconditions.checkArgument(user.canCreatePromotedAuction(maxPromoted),
                    String.format("User %s cannot create promoted auction", user.getDisplayName()));
        }
    }

    @Override
    public Class<? extends Command<Long>> commandClass() {
        return CreateAuctionCommand.class;
    }
}
