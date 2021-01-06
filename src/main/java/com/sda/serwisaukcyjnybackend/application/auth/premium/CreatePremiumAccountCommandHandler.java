package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.google.common.base.Preconditions;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.application.pay.CreateOrderRequest;
import com.sda.serwisaukcyjnybackend.application.pay.PaymentStrategy;
import com.sda.serwisaukcyjnybackend.domain.user.PremiumOrder;
import com.sda.serwisaukcyjnybackend.domain.user.PremiumOrderRepository;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class CreatePremiumAccountCommandHandler implements CommandHandler<CreatePremiumAccountOrder, String> {
    private final UserRepository userRepository;
    private final PaymentStrategy paymentStrategy;
    private final PremiumOrderRepository premiumOrderRepository;

    @Override
    @Transactional
    public CommandResult<String> handle(@Valid CreatePremiumAccountOrder command) {
        var user = userRepository.getOne(command.getUserId());
        Preconditions.checkArgument(!premiumOrderRepository.existsByUserId(user.getId()),
                String.format("User: %s has already order premium account", user.getId()));
        var request = new CreateOrderRequest(user);
        var response = paymentStrategy.createOrder(request);
        var premiumOrder = new PremiumOrder(response.getOrderId(), user);

        premiumOrderRepository.save(premiumOrder);
        return CommandResult.created(response.getRedirectUri());
    }

    @Override
    public Class<? extends Command<String>> commandClass() {
        return CreatePremiumAccountOrder.class;
    }
}
