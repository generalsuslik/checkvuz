package checkvuz.checkvuz.utils.image.service;

import checkvuz.checkvuz.utils.image.assembler.ImageModelAssembler;
import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.exception.ImageNotFoundByIdException;
import checkvuz.checkvuz.utils.image.exception.ImageNotFoundByTitleException;
import checkvuz.checkvuz.utils.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
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
@Slf4j
public class ImageService implements ImageServiceInterface {

    @Value("${file.image.university.path}")
    private String IMAGE_FILE_PATH;

    @Value("${http.url}")
    private String BASE_URL;

    private final ImageModelAssembler imageModelAssembler;
    private final ImageRepository imageRepository;

    @Override
    public Image saveImageToStorage(MultipartFile imageFile, String service) throws IOException {

        // service: "universities", "users", etc
        Path dir = Path.of(IMAGE_FILE_PATH + service + "/");
        if (Files.notExists(dir)) {
            Files.createDirectory(dir);
        }

        UUID uuidPart = UUID.randomUUID();
        String imageTitle = uuidPart + imageFile.getOriginalFilename();
        String imagePath = IMAGE_FILE_PATH + service + "/" + imageTitle;
        String imageUrl = BASE_URL + "/images/" + imageTitle;

        log.info(imageFile.getContentType());
        Image image = imageRepository.save(
                Image.builder()
                        .title(imageTitle)
                        .type(imageFile.getContentType())
                        .imagePath(imagePath)
                        .imageUrl(imageUrl)
                        .createdAt(new Date())
                        .build()
        );

        imageFile.transferTo(new File(imagePath));

        return image;
    }

    @Override
    public Image saveImageToStorage(MultipartFile imageFile, String service, boolean isDefault) throws IOException {

        String imageTitle = imageFile.getOriginalFilename();
        String imagePath = IMAGE_FILE_PATH + "default" + service + imageTitle;
        String imageUrl = BASE_URL + "/images/default" + imageTitle;

        Image image = imageRepository.save(
                Image.builder()
                        .title(imageTitle)
                        .type(imageFile.getContentType())
                        .imagePath(imagePath)
                        .imageUrl(imageUrl)
                        .createdAt(new Date())
                        .isDefault(true)
                        .build()
        );

        imageFile.transferTo(new File(imagePath));

        return image;
    }

    @Override
    public Image getImageData(String imageTitle) {

        return imageRepository.findByTitle(imageTitle).orElseThrow(() -> new ImageNotFoundByTitleException(imageTitle));
    }

    @Override
    public Image getImageDataById(Long imageId) {

        return imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundByIdException(imageId));
    }

    @Override
    public Image getDefaultImageData() {

        return getImageData("default_user_image.jpg");
    }

    @Override
    public byte[] getImage(String imageTitle) throws IOException {

        Image image = getImageData(imageTitle);
        String imagePath = image.getImagePath();

        return Files.readAllBytes(new File(imagePath).toPath());
    }

    @Override
    public void deleteImage(String imageTitle) throws IOException {

        Image image = getImageData(imageTitle);
        Path imagePath = Path.of(image.getImagePath());

        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
        }
    }

    @Override
    public EntityModel<Image> convertImageToModel(Long imageId) {

        Image image = getImageDataById(imageId);
        return imageModelAssembler.toModel(image);
    }
}
