package com.sda.serwisaukcyjnybackend.config.auth.cors;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("auth.cors")
@NoArgsConstructor
@Data
public class CorsProperties {
    private String allowedOrigin;
}
