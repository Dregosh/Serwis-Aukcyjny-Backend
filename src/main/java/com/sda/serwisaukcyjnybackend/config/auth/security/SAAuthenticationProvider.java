package com.sda.serwisaukcyjnybackend.config.auth.security;

import org.keycloak.adapters.springsecurity.account.KeycloakRole;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class SAAuthenticationProvider implements AuthenticationProvider {
    private static final String USER_ID = "userId";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (String role : token.getAccount().getRoles()) {
            grantedAuthorities.add(new KeycloakRole(role));
        }

        return new UsernamePasswordAuthenticationToken(mapToken(token, grantedAuthorities), "N/A", grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SAUserDetails.class == authentication;
    }

    private UserDetails mapToken(KeycloakAuthenticationToken token, List<GrantedAuthority> grantedAuthorities) {
        var account = token.getAccount();
        var context = account.getKeycloakSecurityContext();
        var accessToken = context.getToken();

        return new SAUserDetails(accessToken.getPreferredUsername(), extractUserIdFromToken(accessToken),
                accessToken.getSubject(), grantedAuthorities);
    }

    private String extractUserIdFromToken(AccessToken token) {
        var claims = (ArrayList<String>) token.getOtherClaims().get(USER_ID);
        return claims.get(0);
    }
}
