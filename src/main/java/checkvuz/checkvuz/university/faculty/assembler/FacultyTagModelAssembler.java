package checkvuz.checkvuz.university.faculty.assembler;

import checkvuz.checkvuz.university.faculty.controller.FacultyTagController;
import checkvuz.checkvuz.university.faculty.entity.FacultyTag;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FacultyTagModelAssembler implements RepresentationModelAssembler<FacultyTag, EntityModel<FacultyTag>> {
    @Override
    @NonNull
    public EntityModel<FacultyTag> toModel(@NonNull FacultyTag facultyTag) {
        return EntityModel.of(facultyTag,
                linkTo(methodOn(FacultyTagController.class).getFacultyTag(facultyTag.getId())).withSelfRel(),
                linkTo(methodOn(FacultyTagController.class).getFacultyTags()).withRel("faculty_tags")
        );
    }
}
