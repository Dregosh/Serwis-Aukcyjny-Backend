package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEmailConfirmCommandHandlerTest {
    @Mock
    VerificationCodeRepository verificationCodeRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    KeycloakService keycloakService;

    @InjectMocks
    UpdateEmailConfirmCommandHandler handler;

    @Test
    void shouldThrowExceptionWhenInvalidCode() {
        //given
        UpdateEmailConfirmCommand command = this.prepareCommand();
        when(this.verificationCodeRepository.findByCode(command.getToken()))
                .thenThrow(InvalidVerificationCodeException.class);

        //when & then
        assertThrows(InvalidVerificationCodeException.class,
                     () -> handler.handle(command));
    }

    UpdateEmailConfirmCommand prepareCommand() {
        return new UpdateEmailConfirmCommand("sampleToken");
    }
}
