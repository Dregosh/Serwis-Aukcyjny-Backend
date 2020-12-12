package com.sda.serwisaukcyjnybackend.config.auth.keycloak;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "auth.keycloak.management")
public class KeycloakProperties {
    private String clientId;
    private String serverUrl;
    private String realm;
    private String masterRealm;
    private String username;
    private String password;
}
