package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.application.auth.exception.UserAlreadyExistException;
import com.sda.serwisaukcyjnybackend.application.auth.register.RegisterUserCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateEmailConfirmCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateEmailRequestCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateUserCommand;
import com.sda.serwisaukcyjnybackend.application.auth.verification.ResendVerificationCodeCommand;
import com.sda.serwisaukcyjnybackend.application.auth.verification.VerifyUserCommand;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final CommandDispatcher commandDispatcher;

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserAlreadyExist() {
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleInvalidVerificationCode() {
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterUserCommand registerUserCommand) {
        commandDispatcher.handle(registerUserCommand);
    }

    @PostMapping("/email-verification")
    public void verifyEmail(@RequestBody @Valid VerifyUserCommand verifyUserCommand) {
        commandDispatcher.handle(verifyUserCommand);
    }

    @PostMapping("/resending-verification-code")
    public void resendVerificationCode(@RequestBody
                                       @Valid ResendVerificationCodeCommand resendVerificationCodeCommand) {
        commandDispatcher.handle(resendVerificationCodeCommand);
    }
}
