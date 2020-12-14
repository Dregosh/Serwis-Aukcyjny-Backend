package com.sda.serwisaukcyjnybackend.view.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserExistData {
    private boolean emailExist;
    private boolean displayNameExist;
}
