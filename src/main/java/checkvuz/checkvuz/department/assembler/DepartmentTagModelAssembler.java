package checkvuz.checkvuz.department.assembler;

import checkvuz.checkvuz.department.controller.DepartmentTagController;
import checkvuz.checkvuz.department.entity.DepartmentTag;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DepartmentTagModelAssembler
        implements RepresentationModelAssembler<DepartmentTag, EntityModel<DepartmentTag>> {
    @Override
    @NonNull
    public EntityModel<DepartmentTag> toModel(@NonNull DepartmentTag departmentTag) {
        return EntityModel.of(departmentTag,
                linkTo(methodOn(DepartmentTagController.class).getDepartmentTag(departmentTag.getId())).withSelfRel(),
                linkTo(methodOn(DepartmentTagController.class).getDepartmentTags()).withRel("department_tags"));
    }
}
