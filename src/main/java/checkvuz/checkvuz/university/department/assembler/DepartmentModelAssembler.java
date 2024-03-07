package checkvuz.checkvuz.university.department.assembler;

import checkvuz.checkvuz.university.department.controller.DepartmentController;
import checkvuz.checkvuz.university.department.entity.Department;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DepartmentModelAssembler implements RepresentationModelAssembler<Department, EntityModel<Department>> {
    @Override
    @NonNull
    public EntityModel<Department> toModel(@NonNull Department department) {
        return EntityModel.of(department,
                linkTo(methodOn(DepartmentController.class).getDepartment(department.getId())).withSelfRel(),
                linkTo(methodOn(DepartmentController.class).getDepartments()).withRel("departments"));
    }
}
