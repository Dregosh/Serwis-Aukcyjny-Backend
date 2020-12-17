package com.sda.serwisaukcyjnybackend.application.auth.update;

import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserCommand implements Command<Void> {
    @Email
    private String email;
    @NotNull
    private String displayName;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private Address address;
}
