package checkvuz.checkvuz.university.university.service;

import checkvuz.checkvuz.university.university.entity.UniversityImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UniversityImageServiceInterface {

    UniversityImage saveImageToStorage(MultipartFile imageFile) throws IOException;

    UniversityImage getUniversityImageData(String imageTitle) throws IOException;

    byte[] getImage(String imageName) throws IOException;

    void deleteImage(String imageTitle) throws IOException;
}
