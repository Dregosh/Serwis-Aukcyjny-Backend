package com.sda.serwisaukcyjnybackend.application.auth.register;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.UserAlreadyExistException;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.*;
import com.sda.serwisaukcyjnybackend.domain.user.event.UserRegistered;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserCommandHandlerTest {
    @Mock
    KeycloakService keycloakService;
    @Mock
    UserRepository userRepository;
    @Mock
    VerificationCodeRepository verificationCodeRepository;
    @Mock
    ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    RegisterUserCommandHandler handler;

    @Test
    void shouldRegisterUser() {
        //given
        var command = prepareCommand();
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(prepareUserFromCommand(command));
        when(keycloakService.addUser(any())).thenReturn("test");
        when(verificationCodeRepository.save(any())).thenReturn(new VerificationCode());
        doNothing().when(applicationEventPublisher).publishEvent(any(UserRegistered.class));

        //when
        handler.handle(command);

    }

    @Test
    void shouldThrowUserAlreadyExist() {
        //given
        var command = prepareCommand();
        when(userRepository.existsByEmail(any())).thenReturn(true);

        //when && then
        assertThrows(UserAlreadyExistException.class, () -> handler.handle(command));
    }

    RegisterUserCommand prepareCommand(){
        var command = new RegisterUserCommand();
        command.setAddress(new Address());
        command.setFirstName("test");
        command.setLastName("test");
        command.setEmail("test@test");
        command.setPassword(new char[] {'a', 's', 'd'});
        return command;
    }

    User prepareUserFromCommand(RegisterUserCommand command) {
        return new User(command.getEmail(), command.getFirstName(),
                command.getLastName(), command.getDisplayName(),
                command.getAddress(), AccountStatus.ACTIVE, AccountType.NORMAL);
    }
}