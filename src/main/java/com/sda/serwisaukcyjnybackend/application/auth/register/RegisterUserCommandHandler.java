package com.sda.serwisaukcyjnybackend.application.auth.register;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.UserAlreadyExistException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class RegisterUserCommandHandler implements CommandHandler<RegisterUserCommand, Void> {
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid RegisterUserCommand command) {

        if (userRepository.existsByEmail(command.getEmail())) {
            throw new UserAlreadyExistException();
        }

        var user = new User(command.getEmail(), command.getFirstName(),
                command.getLastName(), command.getDisplayName(),
                command.getAddress(), AccountStatus.ACTIVE, AccountType.NORMAL);
        user.validateDisplayName();
        user = userRepository.save(user);

        keycloakService.addUser(UserRepresentationMapper.mapFromRegister(command, user.getId()));

        var verificationCode = new VerificationCode(user);
        verificationCodeRepository.save(verificationCode);

        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return RegisterUserCommand.class;
    }
}
