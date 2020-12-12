package com.sda.serwisaukcyjnybackend.config.auth.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakResolver {

    @Bean
    public KeycloakSpringBootConfigResolver keycloakSpringBootProperties() {
        return new KeycloakSpringBootConfigResolver();
    }
}
