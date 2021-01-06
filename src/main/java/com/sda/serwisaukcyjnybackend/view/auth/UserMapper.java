package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.domain.user.AccountType;
import com.sda.serwisaukcyjnybackend.domain.user.User;

public class UserMapper {

    public static EditUserDTO mapToEditUserDTO(User user, boolean canBuyPremiumAccount) {
        return EditUserDTO.builder()
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .canBuyPremiumAccount(canBuyPremiumAccount)
                .premiumAccount(user.getAccountType() == AccountType.PREMIUM)
                .premiumAccountExpiration(user.getPremiumAccountExpiration())
                .build();
    }

}
