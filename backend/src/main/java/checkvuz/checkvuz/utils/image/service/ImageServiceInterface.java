package checkvuz.checkvuz.utils.image.service;

import checkvuz.checkvuz.utils.image.entity.Image;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageServiceInterface {

    Image saveImageToStorage(MultipartFile imageFile, String service) throws IOException;

    Image saveImageToStorage(MultipartFile imageFile, String service, boolean isDefault) throws IOException;

    Image getImageData(String imageTitle);

    Image getImageDataById(Long imageId);

    Image getDefaultImageData();

    byte[] getImage(String imageName) throws IOException;

    void deleteImage(String imageTitle) throws IOException;

    EntityModel<Image> convertImageToModel(Long ImageId);
}
