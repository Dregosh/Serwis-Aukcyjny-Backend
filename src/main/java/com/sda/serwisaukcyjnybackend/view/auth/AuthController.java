package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.application.auth.exception.InvalidVerificationCodeException;
import com.sda.serwisaukcyjnybackend.application.auth.exception.UserAlreadyExistException;
import com.sda.serwisaukcyjnybackend.application.auth.logout.LogoutUserCommand;
import com.sda.serwisaukcyjnybackend.application.auth.register.RegisterUserCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.*;
import com.sda.serwisaukcyjnybackend.application.auth.verification.ResendVerificationCodeCommand;
import com.sda.serwisaukcyjnybackend.application.auth.verification.VerifyUserCommand;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.config.auth.security.SAUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService.getLoggedUser;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final CommandDispatcher commandDispatcher;
    private final UserService userService;

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
    public void resendVerificationCode() {
        commandDispatcher
                .handle(new ResendVerificationCodeCommand(getLoggedUser().getEmail()));
    }

    @PostMapping("/logout")
    public void logoutUser() {
        commandDispatcher.handle(new LogoutUserCommand(getLoggedUser().getPrincipalId()));
    }

    @GetMapping("/existing/{email}/{displayName}")
    public UserExistData checkIfUserExist(@PathVariable(name = "email") String email,
                                          @PathVariable(name = "displayName")
                                                  String displayName) {
        return userService.checkIfUserExist(email, displayName);
    }

    @GetMapping("/edit-user")
    public EditUserDTO getEditUserData() {
        SAUserDetails loggedUser = AuthenticatedService.getLoggedUserInfo().orElseThrow();
        return this.userService.getEditUserData(loggedUser.getUserId());
    }

    @PostMapping("/edit-user/update-insensitive-data")
    public void update(@RequestBody @Valid UpdateUserCommand command) {
        this.commandDispatcher.handle(command);
    }

    @PostMapping("/edit-user/update-email-request")
    public void updateEmailRequest(
            @RequestBody @Valid UpdateEmailRequestCommand command) {
        this.commandDispatcher.handle(command);
    }

    @PostMapping("/update-email-confirmation")
    public void verifyUpdatedEmail(
            @RequestBody @Valid UpdateEmailConfirmCommand command) {
        this.commandDispatcher.handle(command);
    }

    @PostMapping("/edit-user/change-password-request")
    public void changePasswordRequest(ChangePasswordRequestCommand command) {
        this.commandDispatcher.handle(command);
    }

    @PostMapping("/change-password-confirmation")
    public void saveChangedPassword(
            @RequestBody @Valid ChangePasswordCommand command) {
        this.commandDispatcher.handle(command);
    }
}
