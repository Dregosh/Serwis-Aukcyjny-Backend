package com.sda.serwisaukcyjnybackend.view.edituser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditUserDTO {
    private String email;
    private String displayName;
    private String firstName;
    private String lastName;
    private String addressCity;
    private String addressState;
    private String addressStreet;
    private String addressNumber;
    private String addressPostal;
}
