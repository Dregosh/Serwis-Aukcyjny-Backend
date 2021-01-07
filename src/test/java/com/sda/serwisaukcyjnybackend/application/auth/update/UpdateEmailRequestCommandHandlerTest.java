package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEmailRequestCommandHandlerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    VerificationCodeRepository verificationCodeRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    UpdateEmailRequestCommandHandler handler;

    @Test
    void shouldSendNewVerificationCodeToUserEmail() {
       /* //given
        UpdateEmailRequestCommand command = this.prepareCommand();
        User user = this.prepareUser(AccountType.NORMAL, 0);
        when(userRepository.findById(AuthenticatedService.getLoggedUser().getUserId()))
                .thenReturn(Optional.of(user));
        when(verificationCodeRepository.save(any())).thenReturn(new VerificationCode());
        //when & then
        handler.handle(command);*/
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
       /* //given
        UpdateEmailRequestCommand command = this.prepareCommand();
        when(userRepository.findById(AuthenticatedService.getLoggedUser().getUserId()))
                .thenReturn(Optional.empty());

        //when & then
        assertThrows(NoSuchElementException.class, () -> handler.handle(command));*/
    }

    User prepareUser(AccountType accountType, Integer promotedAuction) {
        User user = new User();
        user.setAddress(new Address());
        user.setAccountType(accountType);
        user.setPromotedAuctionsCount(promotedAuction);
        return user;
    }

    UpdateEmailRequestCommand prepareCommand() {
        return new UpdateEmailRequestCommand("newEmail");
    }

}
