package com.sda.serwisaukcyjnybackend.application.auth;

import com.sda.serwisaukcyjnybackend.config.auth.security.SAUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuthenticatedService {

    public static Optional<SAUserDetails> getLoggedUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return Optional.ofNullable((SAUserDetails) authentication.getPrincipal());
        }
        return Optional.empty();
    }
}
