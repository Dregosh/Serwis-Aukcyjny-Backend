package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.sda.serwisaukcyjnybackend.application.auth.exception.PremiumOrderNotFoundException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.application.pay.CompleteOrderRequest;
import com.sda.serwisaukcyjnybackend.application.pay.PaymentStrategy;
import com.sda.serwisaukcyjnybackend.domain.user.PremiumOrderRepository;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.PremiumAccountPurchased;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class CompletePremiumAccountOrderHandler implements CommandHandler<CompletePremiumAccountOrder, String> {
    private static final String AUTH_MY_ACCOUNT = "/auth/my-account";
    private final UserRepository userRepository;
    private final PaymentStrategy paymentStrategy;
    private final PremiumOrderRepository premiumOrderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${app.premium.duration}")
    protected int durationDays;
    @Value("${app.web.guiUrl}")
    protected String guiUrl;



    @Override
    @Transactional
    public CommandResult<String> handle(@Valid CompletePremiumAccountOrder command) {
        var request = new CompleteOrderRequest(command.getPayerId(), command.getPaymentId());
        var response = paymentStrategy.completeOrder(request);
        var order = premiumOrderRepository.findByOrderId(response.getOrderId())
                .orElseThrow(() -> new PremiumOrderNotFoundException(response.getOrderId()));
        var user = order.getUser();
        user.setPremiumAccount(durationDays);
        userRepository.save(user);
        premiumOrderRepository.delete(order);
        eventPublisher.publishEvent(new PremiumAccountPurchased(user.getDisplayName(), user.getEmail(),
                user.getPremiumAccountExpiration()));
        return CommandResult.created(guiUrl + AUTH_MY_ACCOUNT);
    }

    @Override
    public Class<? extends Command<String>> commandClass() {
        return CompletePremiumAccountOrder.class;
    }
}
