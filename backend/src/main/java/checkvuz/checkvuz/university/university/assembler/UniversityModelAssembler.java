package checkvuz.checkvuz.university.university.assembler;

import checkvuz.checkvuz.university.university.controller.UniversityController;
import checkvuz.checkvuz.university.university.entity.University;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UniversityModelAssembler implements RepresentationModelAssembler<University, EntityModel<University>> {
    @Override
    @NonNull
    public EntityModel<University> toModel(@NonNull University university) {
        return EntityModel.of(university,
                linkTo(methodOn(UniversityController.class).getUniversity(university.getId())).withSelfRel(),
                linkTo(methodOn(UniversityController.class).getUniversities()).withRel("universities")
        );
    }
}
