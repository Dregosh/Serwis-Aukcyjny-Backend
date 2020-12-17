package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class ChangePasswordCommandHandler
        implements CommandHandler<ChangePasswordCommand, Void> {
    private final VerificationCodeRepository verificationCodeRepository;
    private final KeycloakService keycloakService;

    @Override
    public CommandResult<Void> handle(@Valid ChangePasswordCommand command) {
        this.verificationCodeRepository
                .findByCode(command.getToken())
                .ifPresentOrElse(
                        code -> executePasswordChange(code, command.getNewPassword()),
                        () -> {
                            throw new InvalidVerificationCodeException();
                        });
        return CommandResult.ok();
    }

    private void executePasswordChange(VerificationCode code,
                                       String newPassword) {
        this.keycloakService.updateUserPassword(code.getUserEmail(), newPassword);
        this.verificationCodeRepository.delete(code);
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return ChangePasswordCommand.class;
    }
}
