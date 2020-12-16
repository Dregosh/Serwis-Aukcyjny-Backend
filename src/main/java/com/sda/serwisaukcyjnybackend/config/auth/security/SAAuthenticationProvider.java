package com.sda.serwisaukcyjnybackend.config.auth.security;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class SAAuthenticationProvider extends KeycloakAuthenticationProvider {
    private static final String USER_ID = "userId";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken)authentication;
        List<GrantedAuthority> authorities = token.getAccount().getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(mapToken(token, authorities), "N/A", authorities);
    }

    private UserDetails mapToken(KeycloakAuthenticationToken token, List<GrantedAuthority> grantedAuthorities) {
        var account = token.getAccount();
        var context = account.getKeycloakSecurityContext();
        var accessToken = context.getToken();

        return new SAUserDetails(accessToken.getPreferredUsername(), extractUserIdFromToken(accessToken),
                accessToken.getSubject(), grantedAuthorities);
    }

    private Long extractUserIdFromToken(AccessToken token) {
        var value = (Integer) token.getOtherClaims().get(USER_ID);
        return value.longValue();
    }
}
