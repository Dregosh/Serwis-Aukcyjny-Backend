package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.PremiumOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class CancelPremiumAccountOrderHandler implements CommandHandler<CancelPremiumAccountOrder, String> {
    private static final String AUTH_MY_ACCOUNT = "/auth/my-account";
    private final PremiumOrderRepository premiumOrderRepository;

    @Value("${app.web.guiUrl}")
    protected String guiUrl;

    @Override
    @Transactional
    public CommandResult<String> handle(@Valid CancelPremiumAccountOrder command) {
        premiumOrderRepository.findByOrderId(command.getOrderId())
                .ifPresent(premiumOrderRepository::delete);
        return CommandResult.created(guiUrl + AUTH_MY_ACCOUNT);
    }

    @Override
    public Class<? extends Command<String>> commandClass() {
        return CancelPremiumAccountOrder.class;
    }
}
