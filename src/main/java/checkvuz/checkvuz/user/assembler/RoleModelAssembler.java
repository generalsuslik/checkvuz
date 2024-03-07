package checkvuz.checkvuz.user.assembler;

import checkvuz.checkvuz.user.controller.RoleController;
import checkvuz.checkvuz.user.entity.UserRole;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleModelAssembler implements RepresentationModelAssembler<UserRole, EntityModel<UserRole>> {

    @Override
    @NonNull
    public EntityModel<UserRole> toModel(@NonNull UserRole role) {
        return EntityModel.of(role,
                linkTo(methodOn(RoleController.class).getRoleById(role.getId())).withSelfRel(),
                linkTo(methodOn(RoleController.class).getRoles()).withRel("roles"));
    }
}
