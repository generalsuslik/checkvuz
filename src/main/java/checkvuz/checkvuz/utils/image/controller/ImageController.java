package checkvuz.checkvuz.utils.image.controller;

import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.service.ImageServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageService;

    @GetMapping("")
    public CollectionModel<EntityModel<Image>> getAllImages() {
        return imageService.getAllImages();
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Image>> createImage(@RequestParam("image") MultipartFile imageToCreate)
            throws IOException {

        return imageService.createImage(imageToCreate);
    }

    @GetMapping("json/{imageId}")
    public EntityModel<Image> getImageJSON(@PathVariable Long imageId) {
        return imageService.getImageJSON(imageId);
    }

    @GetMapping("image/{imageId}")
    public ResponseEntity<byte[]> getImageByTitle(@PathVariable Long imageId) throws IOException {
        return imageService.getImageFile(imageId);
    }
}
