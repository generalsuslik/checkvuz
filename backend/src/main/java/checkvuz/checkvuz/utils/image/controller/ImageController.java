package checkvuz.checkvuz.utils.image.controller;

import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("")
    public ResponseEntity<?> createImage(@RequestParam("image")MultipartFile imageToCreate) throws IOException {

        Image image = imageService.saveImageToStorage(imageToCreate, "/users/");
        return ResponseEntity.ok(image);
    }

    @GetMapping("/{imageTitle}")
    public ResponseEntity<?> getImage(@PathVariable String imageTitle) throws IOException {

        byte[] imageByteArray = imageService.getImage(imageTitle);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageService.getImageData(imageTitle).getType()))
                .body(imageByteArray);
    }

    @GetMapping("/data/{imageTitle}")
    public ResponseEntity<Image> getImageData(@PathVariable String imageTitle) {

        Image image = imageService.getImageData(imageTitle);
        return ResponseEntity.status(HttpStatus.OK).body(image);
    }
}
