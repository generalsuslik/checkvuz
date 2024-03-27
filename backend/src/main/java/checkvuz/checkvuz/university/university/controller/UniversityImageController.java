package checkvuz.checkvuz.university.university.controller;

import checkvuz.checkvuz.university.university.entity.UniversityImage;
import checkvuz.checkvuz.university.university.service.UniversityImageService;
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
public class UniversityImageController {

    private final UniversityImageService universityImageService;

    @PostMapping("")
    public ResponseEntity<?> createUniversityImage(@RequestParam("image") MultipartFile image) throws IOException {

        System.out.println(image);
        UniversityImage universityImage = universityImageService.saveImageToStorage(image);
        return ResponseEntity.ok(universityImage);
    }

    @GetMapping("/{imageTitle}")
    public ResponseEntity<?> getUniversityImage(@PathVariable String imageTitle) throws IOException {

        byte[] imageByteArray = universityImageService.getImage(imageTitle);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageByteArray);
    }

    @GetMapping("/data/{imageTitle}")
    public ResponseEntity<UniversityImage> getUniversityImageData(@PathVariable String imageTitle) {

        UniversityImage universityImage = universityImageService.getUniversityImageData(imageTitle);
        return ResponseEntity.status(HttpStatus.OK).body(universityImage);
    }
}
