package com.sda.serwisaukcyjnybackend.application.auth.register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.view.shared.validators.Name;
import com.sda.serwisaukcyjnybackend.view.shared.validators.Password;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Arrays;

@NoArgsConstructor
@Data
public class RegisterUserCommand implements Command<Void> {
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = ".{6,}")
    private String displayName;
    @NotEmpty
    @Name
    private String firstName;
    @NotEmpty
    @Name
    private String lastName;
    private Address address;
    @NotNull
    @Password
    private char[] password;

    @JsonIgnore
    public void utilizePassword() {
        Arrays.fill(password, '*');
    }
}
