package com.sda.serwisaukcyjnybackend.view.auction;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @GetMapping("/{photoName}")
    public Resource getPhoto(@PathVariable(name = "photoName") String photoName) {
        return photoService.getPhotoById(photoName);
    }
}
