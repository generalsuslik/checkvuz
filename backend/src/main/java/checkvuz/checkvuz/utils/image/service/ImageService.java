package checkvuz.checkvuz.utils.image.service;

import checkvuz.checkvuz.utils.image.assembler.ImageModelAssembler;
import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.exception.ImageNotFoundByIdException;
import checkvuz.checkvuz.utils.image.exception.ImageNotFoundByTitleException;
import checkvuz.checkvuz.utils.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
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
public class ImageService implements ImageServiceInterface {

    private static final String IMAGE_FILE_PATH =
            "/home/generalsuslik/Projects/check_vuz/backend/src/main/resources/static/images/universities/";
    private static final String BASE_URL = "http://localhost:8080/api/images/";

    private final ImageModelAssembler imageModelAssembler;
    private final ImageRepository imageRepository;

    @Override
    public Image saveImageToStorage(MultipartFile imageFile) throws IOException {

        Path dir = Path.of(IMAGE_FILE_PATH);
        if (Files.notExists(dir)) {
            Files.createDirectory(dir);
        }

        UUID uuidPart = UUID.randomUUID();
        String imageName = uuidPart + imageFile.getOriginalFilename();
        String imagePath = IMAGE_FILE_PATH + imageName;
        String imageUrl = BASE_URL + imageName;

        Image image = imageRepository.save(
                Image.builder()
                        .title(imageName)
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
    public Image getImageData(String imageTitle) {

        return imageRepository.findByTitle(imageTitle).orElseThrow(() -> new ImageNotFoundByTitleException(imageTitle));
    }

    @Override
    public Image getImageDataById(Long imageId) {

        return imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundByIdException(imageId));
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
