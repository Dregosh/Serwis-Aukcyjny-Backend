package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.domain.shared.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditUserDTO {
    private String email;
    private String displayName;
    private String firstName;
    private String lastName;
    private Address address;
    private boolean premiumAccount;
    private boolean canBuyPremiumAccount;
    private LocalDate premiumAccountExpiration;
}
