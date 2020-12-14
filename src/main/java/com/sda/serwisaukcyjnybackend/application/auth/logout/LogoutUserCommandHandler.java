package com.sda.serwisaukcyjnybackend.application.auth.logout;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class LogoutUserCommandHandler implements CommandHandler<LogoutUserCommand, Void> {
    private final KeycloakService keycloakService;

    @Override
    public CommandResult<Void> handle(@Valid LogoutUserCommand command) {
        keycloakService.logout(command.getPrincipalId());

        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return LogoutUserCommand.class;
    }
}
