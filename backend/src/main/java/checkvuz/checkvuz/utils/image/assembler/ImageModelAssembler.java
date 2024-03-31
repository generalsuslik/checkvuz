package checkvuz.checkvuz.utils.image.assembler;

import checkvuz.checkvuz.utils.image.controller.ImageController;
import checkvuz.checkvuz.utils.image.entity.Image;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ImageModelAssembler implements RepresentationModelAssembler<Image, EntityModel<Image>> {
    @Override
    @NonNull
    public EntityModel<Image> toModel(@NonNull Image image) {

        return EntityModel.of(image,
                linkTo(methodOn(ImageController.class).getImageData(image.getTitle())).withSelfRel()
                );
    }
}
