package com.sda.serwisaukcyjnybackend.application.auth.logout;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class LogoutUserCommandHandlerTest {
    @Mock
    KeycloakService keycloakService;

    @InjectMocks
    LogoutUserCommandHandler handler;

    @Test
    void shouldLogoutUser() {
        //given
        var command = new LogoutUserCommand("testId");
        doNothing().when(keycloakService).logout(anyString());

        //when && then
        handler.handle(command);
    }

}