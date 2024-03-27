package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.university.entity.UniversityImage;
import checkvuz.checkvuz.university.university.repository.UniversityImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UniversityImageService implements UniversityImageServiceInterface {

    private final UniversityImageRepository universityImageRepository;

    @Override
    public UniversityImage saveImageToStorage(MultipartFile imageFile) throws IOException {

        String IMAGE_FILE_PATH =
                "/home/generalsuslik/Projects/check_vuz/backend/src/main/resources/static/images/universities/";
        String BASE_URL = "http://localhost:8080/api/images/";

        Path dir = Path.of(IMAGE_FILE_PATH);
        if (Files.notExists(dir)) {
            Files.createDirectory(dir);
        }

        UUID uuidPart = UUID.randomUUID();
        String imageName = uuidPart + imageFile.getOriginalFilename();
        String imagePath = IMAGE_FILE_PATH + imageName;
        String imageUrl = BASE_URL + imageName;

        UniversityImage universityImage = universityImageRepository.save(
                UniversityImage.builder()
                        .title(imageName)
                        .type(imageFile.getContentType())
                        .imagePath(imagePath)
                        .imageUrl(imageUrl)
                        .created_at(new Date())
                        .build()
        );

        imageFile.transferTo(new File(imagePath));

        return universityImage;
    }

    @Override
    public UniversityImage getUniversityImageData(String imageTitle) {

        return universityImageRepository.findUniversityImageByTitle(imageTitle)
                .orElseThrow();
    }

    @Override
    public byte[] getImage(String imageName) throws IOException {

        UniversityImage universityImage = getUniversityImageData(imageName);

        String imagePath = universityImage.getImagePath();

        // returns byte[] array
        return Files.readAllBytes(new File(imagePath).toPath());
    }

    @Override
    public void deleteImage(String imageTitle) throws IOException {

        UniversityImage universityImage = getUniversityImageData(imageTitle);
        Path imagePath = Path.of(universityImage.getImagePath());

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
        }
    }
}
