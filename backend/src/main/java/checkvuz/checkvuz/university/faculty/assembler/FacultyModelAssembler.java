package checkvuz.checkvuz.university.faculty.assembler;

import checkvuz.checkvuz.university.faculty.controller.FacultyController;
import checkvuz.checkvuz.university.faculty.entity.Faculty;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FacultyModelAssembler implements RepresentationModelAssembler<Faculty, EntityModel<Faculty>> {

    @Override
    @NonNull
    public EntityModel<Faculty> toModel(@NonNull Faculty faculty) {
        return EntityModel.of(faculty,
                linkTo(methodOn(FacultyController.class).getFaculty(faculty.getId())).withSelfRel(),
                linkTo(methodOn(FacultyController.class).getAllFaculties()).withRel("faculties")
        );
    }
}
