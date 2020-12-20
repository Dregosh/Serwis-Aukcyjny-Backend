package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.view.auth.EditUserDTO;

public class EditUserMapper {

    public static EditUserDTO mapToEditUserDTO(User user) {
        return EditUserDTO.builder()
                          .email(user.getEmail())
                          .displayName(user.getDisplayName())
                          .firstName(user.getFirstName())
                          .lastName(user.getLastName())
                          .address(new Address(
                                  user.getAddress().getCity(),
                                  "województwo",
                                  user.getAddress().getStreet(),
                                  user.getAddress().getNumber(),
                                  user.getAddress().getPostal()
                          ))
                          .build();
    }
}
