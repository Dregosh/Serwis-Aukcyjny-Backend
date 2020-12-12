package com.sda.serwisaukcyjnybackend.application.auth.verification;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VerifyUserCommand implements Command<Void> {
    private String token;
}
