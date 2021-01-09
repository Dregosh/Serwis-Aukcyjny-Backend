package com.sda.serwisaukcyjnybackend.view.auction;

import com.sda.serwisaukcyjnybackend.config.app.photo.PhotoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoProperties photoProperties;

    Resource getPhotoById(String fileName) {
            var path = Paths.get(photoProperties.getDestination() + File.separator + fileName);
            return new FileSystemResource(path);
    }
}
