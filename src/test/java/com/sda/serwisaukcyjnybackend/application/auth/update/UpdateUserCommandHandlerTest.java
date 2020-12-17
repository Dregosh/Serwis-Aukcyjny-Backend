package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.auth.KeycloakService;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.AccountType;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserCommandHandlerTest {
    @Mock
    KeycloakService keycloakService;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UpdateUserCommandHandler handler;

    @Test
    void shouldUpdateUserInsensitiveData() {
        //given
        UpdateUserCommand command = this.prepareCommand();
        when(userRepository.findByEmail(command.getEmail()))
                .thenReturn(Optional.of(this.prepareUser(AccountType.NORMAL, 0)));
        doNothing().when(keycloakService)
                   .updateUserInsensitiveData(command.getEmail(),
                                              command.getFirstName(),
                                              command.getLastName());
        //when & then
        handler.handle(command);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        //given
        UpdateUserCommand command = this.prepareCommand();
        when(userRepository.findByEmail(command.getEmail()))
                .thenReturn(Optional.empty());

        //when & then
        assertThrows(NoSuchElementException.class, () -> handler.handle(command));
    }

    User prepareUser(AccountType accountType, Integer promotedAuction) {
        User user = new User();
        user.setAddress(new Address());
        user.setAccountType(accountType);
        user.setPromotedAuctionsCount(promotedAuction);
        return user;
    }

    UpdateUserCommand prepareCommand() {
        return new UpdateUserCommand(
                "test@email.pl", "testDisplayName", "testFirstName", "testLastname",
                new Address("city", "state", "street", "number", "postal"));
    }
}
