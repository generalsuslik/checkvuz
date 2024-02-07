package checkvuz.checkvuz.utils.image.service;

import checkvuz.checkvuz.utils.image.assembler.ImageModelAssembler;
import checkvuz.checkvuz.utils.image.controller.ImageController;
import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.exception.ImageNotFoundByIdException;
import checkvuz.checkvuz.utils.image.repository.ImageRepository;
import checkvuz.checkvuz.utils.settings.FileConstants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageModelAssembler imageModelAssembler;
    private final ImageRepository imageRepository;

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final static String FOLDER_PATH = FileConstants.FOLDER_PATH;
    private final static String URL_PREFIX = FileConstants.URL_PREFIX;

    @Override
    public CollectionModel<EntityModel<Image>> getAllImages() {

        List<EntityModel<Image>> images = imageRepository.findAll().stream()
                .map(imageModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(images, linkTo(methodOn(ImageController.class).getAllImages()).withSelfRel());
    }

    @Override
    public ResponseEntity<EntityModel<Image>> createImage(MultipartFile imageToCreate) throws IOException {

        String filePath = FOLDER_PATH + imageToCreate.getOriginalFilename();
        String fileUrl = URL_PREFIX + imageToCreate.getOriginalFilename();
        Image imageFile = imageRepository.save(
                Image.builder()
                        .title(imageToCreate.getOriginalFilename())
                        .type(imageToCreate.getContentType())
                        .imagePath(filePath)
                        .imageUrl(fileUrl)
                        .build()
        );

        imageToCreate.transferTo(new File(filePath));
        System.out.println(imageToCreate.getOriginalFilename());
        logger.info("File uploaded successfully: " + imageToCreate.getOriginalFilename());
        EntityModel<Image> entityModel = imageModelAssembler.toModel(imageFile);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Override
    public EntityModel<Image> getImageJSON(Long imageId) {

        Image imageFile = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundByIdException(imageId));

        return imageModelAssembler.toModel(imageFile);
    }

    @Override
    public ResponseEntity<byte[]> getImageFile(Long imageId) throws IOException {

        Image imageFile = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundByIdException(imageId));
        String filePath = imageFile.getImagePath();

        byte[] imageByteArray = Files.readAllBytes(new File(filePath).toPath());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageByteArray);
    }

    @Override
    public ResponseEntity<?> deleteImage(Long imageId) {

        imageRepository.deleteById(imageId);

        return ResponseEntity.noContent().build();
    }
}
