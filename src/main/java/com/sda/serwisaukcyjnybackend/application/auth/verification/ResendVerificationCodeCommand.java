package com.sda.serwisaukcyjnybackend.application.auth.verification;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.Value;

@Value
public class ResendVerificationCodeCommand implements Command<Void> {
    Long id;
}
