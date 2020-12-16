package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class UpdateEmailConfirmCommandHandler
        implements CommandHandler<UpdateEmailConfirmCommand, Void> {
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    public CommandResult<Void> handle(@Valid UpdateEmailConfirmCommand command) {
        this.verificationCodeRepository
                .findByCode(command.getToken())
                .ifPresentOrElse(this::handleValidVerificationCode,
                                 () -> {
                                     throw new InvalidVerificationCodeException();
                                 });
        return CommandResult.ok();
    }

    private void handleValidVerificationCode(VerificationCode code) {
        User user = this.userRepository.findByEmail(code.getUserEmail()).orElseThrow();
        user.setEmail(code.getRelatedEmail());
        this.userRepository.save(user);
        this.verificationCodeRepository.delete(code);
        this.keycloakService.updateUserEmail(code.getUserEmail(), code.getRelatedEmail());
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return UpdateEmailConfirmCommand.class;
    }
}
