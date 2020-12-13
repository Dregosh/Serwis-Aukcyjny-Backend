package com.sda.serwisaukcyjnybackend.application.auth.verification;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserEmailVerified;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class VerifyUserCommandHandler implements CommandHandler<VerifyUserCommand, Void> {
    private final VerificationCodeRepository verificationCodeRepository;
    private final KeycloakService keycloakService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid VerifyUserCommand command) {
        verificationCodeRepository.findByCode(command.getToken())
                .ifPresentOrElse(this::handleValidVerificationCode,
                        () -> {
                            throw new InvalidVerificationCodeException();
                        });

        return CommandResult.ok();
    }

    private void handleValidVerificationCode(VerificationCode verificationCode) {
        keycloakService.verifyUserEmail(verificationCode.getUserEmail());
        verificationCodeRepository.delete(verificationCode);
        eventPublisher.publishEvent(new UserEmailVerified(verificationCode.getUserEmail(),
                verificationCode.getUserDisplayName()));
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return VerifyUserCommand.class;
    }
}
