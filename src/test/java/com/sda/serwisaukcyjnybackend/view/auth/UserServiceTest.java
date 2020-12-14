package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    
    @InjectMocks
    UserService userService;
    
    @ParameterizedTest
    @MethodSource("provideData")
    void shouldReturnUserExist(boolean emailExpected,  boolean nameExpected) {
        //given
        when(userRepository.existsByEmail(anyString())).thenReturn(emailExpected);
        when(userRepository.existsByDisplayName(anyString())).thenReturn(nameExpected);

        //when
        var result = userService.checkIfUserExist("test@test", "testA");

        //then
        assertEquals(nameExpected, result.isDisplayNameExist());
        assertEquals(emailExpected, result.isEmailExist());
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(true, true),
                Arguments.of(true, false),
                Arguments.of(false, true),
                Arguments.of(false, false)

        );
    }
}