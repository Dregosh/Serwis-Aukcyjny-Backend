package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@Data
public class UpdateEmailRequestCommand implements Command<Void> {
    @Email
    private String oldEmail;
    @Email
    private String newEmail;
}
