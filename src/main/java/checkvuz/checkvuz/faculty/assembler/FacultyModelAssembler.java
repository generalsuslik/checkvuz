package checkvuz.checkvuz.faculty.assembler;

import checkvuz.checkvuz.faculty.controller.FacultyController;
import checkvuz.checkvuz.faculty.entity.Faculty;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FacultyModelAssembler implements RepresentationModelAssembler<Faculty, EntityModel<Faculty>> {

    @Override
    public EntityModel<Faculty> toModel(Faculty faculty) {
        return EntityModel.of(faculty,
                linkTo(methodOn(FacultyController.class).getFaculty(faculty.getId())).withSelfRel(),
                linkTo(methodOn(FacultyController.class).getAllFaculties()).withRel("faculties")
        );
    }
}
