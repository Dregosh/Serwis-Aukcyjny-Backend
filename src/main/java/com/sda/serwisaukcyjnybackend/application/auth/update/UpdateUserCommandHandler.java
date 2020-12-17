package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UpdateUserCommandHandler implements CommandHandler<UpdateUserCommand, Void> {
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid UpdateUserCommand command) {
        User user = this.userRepository.findByEmail(command.getEmail())
                                       .orElseThrow(NoSuchElementException::new);
        user.setDisplayName(command.getDisplayName());
        user.setFirstName(command.getFirstName());
        user.setLastName(command.getLastName());
        user.setAddress(command.getAddress());

        this.keycloakService.updateUserInsensitiveData(command.getEmail(),
                                                       command.getFirstName(),
                                                       command.getLastName());
        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return UpdateUserCommand.class;
    }
}
