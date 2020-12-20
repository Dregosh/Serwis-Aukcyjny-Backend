package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.ChangePasswordRequested;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class ChangePasswordRequestCommandHandler
        implements CommandHandler<ChangePasswordRequestCommand, Void> {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid ChangePasswordRequestCommand command) {
        Long loggedUserId = AuthenticatedService.getLoggedUser().getUserId();
        User user = this.userRepository.findById(loggedUserId).orElseThrow();
        VerificationCode verificationCode = new VerificationCode(user);
        this.verificationCodeRepository.save(verificationCode);
        eventPublisher.publishEvent(new ChangePasswordRequested(
                user.getDisplayName(), verificationCode.getCode(), user.getEmail()));
        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return ChangePasswordRequestCommand.class;
    }
}
