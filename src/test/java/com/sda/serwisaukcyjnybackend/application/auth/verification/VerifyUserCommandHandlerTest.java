package com.sda.serwisaukcyjnybackend.application.auth.verification;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserEmailVerified;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifyUserCommandHandlerTest {
    @Mock
    KeycloakService keycloakService;
    @Mock
    VerificationCodeRepository verificationCodeRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    VerifyUserCommandHandler handler;

    @Test
    void shouldVerifyUser() {
        //given
        var command = new VerifyUserCommand(UUID.randomUUID().toString());
        var verificationCode = new VerificationCode(new User());
        when(verificationCodeRepository.findByCode(command.getToken())).thenReturn(Optional.of(verificationCode));
        doNothing().when(keycloakService).verifyUserEmail(any());
        doNothing().when(verificationCodeRepository).delete(any());
        doNothing().when(eventPublisher).publishEvent(any(UserEmailVerified.class));

        //when && then
        handler.handle(command);
    }

    @Test
    void shouldThrowInvalidCodeException() {
        //given
        var command = new VerifyUserCommand(UUID.randomUUID().toString());
        when(verificationCodeRepository.findByCode(command.getToken())).thenReturn(Optional.empty());

        //when && then
        assertThrows(InvalidVerificationCodeException.class, () -> handler.handle(command));
    }

}