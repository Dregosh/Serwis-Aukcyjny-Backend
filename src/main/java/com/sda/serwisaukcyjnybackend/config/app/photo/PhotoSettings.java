package com.sda.serwisaukcyjnybackend.config.app.photo;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@EnableConfigurationProperties(PhotoProperties.class)
@RequiredArgsConstructor
@Log4j2
public class PhotoSettings {
    private final PhotoProperties photoProperties;

    @PostConstruct
    private void createDestinationIfNotExist() {
        File dir = new File(photoProperties.getDestination());
        if(!dir.exists()) {
            log.info("Image directory not exist, creating new at path:{}", photoProperties.getDestination());
            Preconditions.checkArgument(dir.mkdir(), "Could not create image directory");
        }
    }
}
