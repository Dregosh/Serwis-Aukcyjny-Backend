package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.application.auth.exception.UserAlreadyExistException;
import com.sda.serwisaukcyjnybackend.application.auth.register.RegisterUserCommand;
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
    public void handleUserAlreadyExist(){}

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterUserCommand registerUserCommand) {
        commandDispatcher.handle(registerUserCommand);
    }
}
