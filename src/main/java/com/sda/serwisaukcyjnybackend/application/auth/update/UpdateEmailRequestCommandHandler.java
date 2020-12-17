package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandHandler;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.UpdateEmailRequested;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class UpdateEmailRequestCommandHandler
        implements CommandHandler<UpdateEmailRequestCommand, Void> {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public CommandResult<Void> handle(@Valid UpdateEmailRequestCommand command) {
        User user = this.userRepository.findByEmail(command.getOldEmail())
                                       .orElseThrow(NoSuchElementException::new);

        VerificationCode verificationCode = new VerificationCode(user);
        verificationCode.setRelatedEmail(command.getNewEmail());
        verificationCodeRepository.save(verificationCode);
        eventPublisher.publishEvent(new UpdateEmailRequested(user.getDisplayName(),
                                                             verificationCode.getCode(),
                                                             command.getNewEmail()));
        return CommandResult.ok();
    }

    @Override
    public Class<? extends Command<Void>> commandClass() {
        return UpdateEmailRequestCommand.class;
    }
}
