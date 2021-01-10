package com.sda.serwisaukcyjnybackend.application.auth.verification;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
public class ResendVerificationCodeHandler implements CommandHandler<ResendVerificationCodeCommand, Void> {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public CommandResult<Void> handle(@Valid ResendVerificationCodeCommand command) {
        userRepository.findById(command.getId())
                .ifPresent(this::resendVerificationCode);

        return CommandResult.ok();
    }

    private void resendVerificationCode(User user) {
        var verificationCode = new VerificationCode(user);
        verificationCodeRepository.save(verificationCode);
        eventPublisher.publishEvent(new UserRegistered(user.getDisplayName(),
                verificationCode.getCode(), user.getEmail()));
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return ResendVerificationCodeCommand.class;
    }
}
