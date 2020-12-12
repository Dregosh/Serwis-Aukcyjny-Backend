package com.sda.serwisaukcyjnybackend.application.auth.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@NoArgsConstructor
@Data
public class RegisterUserCommand implements Command<Void> {
    @Email
    private String email;
    private String displayName;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private Address address;
    @NotNull
    private char[] password;

    @JsonIgnore
    public void utilizePassword() {
        Arrays.fill(password, '0');
    }
}
