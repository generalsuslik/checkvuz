package checkvuz.checkvuz.utils.image.service;

import checkvuz.checkvuz.utils.image.entity.Image;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    CollectionModel<EntityModel<Image>> getAllImages();

    ResponseEntity<EntityModel<Image>> createImage(MultipartFile imageToCreate) throws IOException;

    EntityModel<Image> getImageJSON(Long imageId);

    ResponseEntity<byte[]> getImageFile(Long imageId) throws IOException;

    ResponseEntity<?> deleteImage(Long imageId);
}
