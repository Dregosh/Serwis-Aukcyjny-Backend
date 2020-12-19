package com.sda.serwisaukcyjnybackend.view.edituser;

import com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService;
import com.sda.serwisaukcyjnybackend.application.auth.update.ChangePasswordCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateEmailConfirmCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateEmailRequestCommand;
import com.sda.serwisaukcyjnybackend.application.auth.update.UpdateUserCommand;
import com.sda.serwisaukcyjnybackend.application.command.CommandDispatcher;
import com.sda.serwisaukcyjnybackend.config.auth.security.SAUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.sda.serwisaukcyjnybackend.application.auth.AuthenticatedService.getLoggedUser;

@RestController
@RequiredArgsConstructor
public class EditUserController {
    private final EditUserService editUserService;
    private final CommandDispatcher commandDispatcher;

    @GetMapping("/api/edit-user")
    public EditUserDTO getEditUserData() {
        SAUserDetails loggedUser =
                AuthenticatedService.getLoggedUserInfo().orElseThrow();
        return this.editUserService.getEditUserData(loggedUser.getUserId());
    }

    @PostMapping("/api/edit-user/update-insensitive-data")
    public void update(@RequestBody @Valid UpdateUserCommand updateUserCommand) {
        this.commandDispatcher.handle(updateUserCommand);
    }

    @PostMapping("/api/edit-user/update-email-request")
    public void updateEmailRequest(
            @RequestBody @Valid UpdateEmailRequestCommand updateEmailRequestCommand) {
        this.commandDispatcher.handle(updateEmailRequestCommand);
    }

    @PostMapping("/api/update-email-confirmation")
    public void verifyUpdatedEmail(
            @RequestBody @Valid UpdateEmailConfirmCommand updateEmailConfirmCommand) {
        this.commandDispatcher.handle(updateEmailConfirmCommand);
    }

    @GetMapping("/api/edit-user/change-password-request")
    public void changePasswordRequest() {
        this.editUserService.sendEmailToChangePasswordForm(getLoggedUser().getUserId());
    }

    @PostMapping("/api/change-password-confirmed")
    public void saveChangedPassword(@RequestBody @Valid ChangePasswordCommand
                                            changePasswordCommand) {
        this.commandDispatcher.handle(changePasswordCommand);
    }
}
