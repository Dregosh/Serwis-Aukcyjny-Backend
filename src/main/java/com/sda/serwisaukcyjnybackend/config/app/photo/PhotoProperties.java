package com.sda.serwisaukcyjnybackend.config.app.photo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.photo")
@Data
public class PhotoProperties {
    private String destination;
}
