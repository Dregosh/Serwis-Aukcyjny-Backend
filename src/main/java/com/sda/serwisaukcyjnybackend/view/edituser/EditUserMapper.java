package com.sda.serwisaukcyjnybackend.view.edituser;

import com.sda.serwisaukcyjnybackend.domain.user.User;

public class EditUserMapper {

    public static EditUserDTO mapToEditUserDTO(User user) {
        return EditUserDTO.builder()
                          .email(user.getEmail())
                          .displayName(user.getDisplayName())
                          .firstName(user.getFirstName())
                          .lastName(user.getLastName())
                          .addressCity(user.getAddress().getCity())
                          .addressState(user.getAddress().getState())
                          .addressStreet(user.getAddress().getStreet())
                          .addressNumber(user.getAddress().getNumber())
                          .addressPostal(user.getAddress().getPostal())
                          .build();
    }
}
