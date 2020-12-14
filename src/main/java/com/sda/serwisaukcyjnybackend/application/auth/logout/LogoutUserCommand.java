package com.sda.serwisaukcyjnybackend.application.auth.logout;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

@Value
public class LogoutUserCommand implements Command<Void> {
    String principalId;
}
