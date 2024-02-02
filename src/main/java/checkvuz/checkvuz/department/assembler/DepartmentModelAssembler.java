package checkvuz.checkvuz.department.assembler;

import checkvuz.checkvuz.department.controller.DepartmentController;
import checkvuz.checkvuz.department.entity.Department;
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
