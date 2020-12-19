package com.sda.serwisaukcyjnybackend.application.auth.verification;

import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResendVerificationCodeHandlerTest {
    @Mock
    UserRepository userRepository;
    @Mock
    VerificationCodeRepository verificationCodeRepository;
    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    ResendVerificationCodeHandler handler;

    @Test
    void shouldResendVerificationCode() {
        //given
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));
        when(verificationCodeRepository.save(any())).thenReturn(new VerificationCode());
        doNothing().when(eventPublisher).publishEvent(any(UserRegistered.class));
        var command = new ResendVerificationCodeCommand("test@test");

        //when
        handler.handle(command);
    }

    @Test
    void shouldNotResendIfEmailNotFound() {
        //given
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        var command = new ResendVerificationCodeCommand("test@test");

        //when
        handler.handle(command);

        //then
        verifyNoInteractions(verificationCodeRepository, eventPublisher);

    }

}