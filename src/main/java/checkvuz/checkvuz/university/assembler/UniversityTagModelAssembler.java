package checkvuz.checkvuz.university.assembler;

import checkvuz.checkvuz.university.controller.UniversityTagController;
import checkvuz.checkvuz.university.entity.UniversityTag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UniversityTagModelAssembler implements
        RepresentationModelAssembler<UniversityTag, EntityModel<UniversityTag>> {

    @Override
    public EntityModel<UniversityTag> toModel(UniversityTag universityTag) {
        return EntityModel.of(universityTag,
                linkTo(methodOn(UniversityTagController.class).getUniversityTag(universityTag.getId())).withSelfRel(),
                linkTo(methodOn(UniversityTagController.class).getUniversityTags()).withRel("university_tags")
        );
    }
}
