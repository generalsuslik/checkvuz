package checkvuz.checkvuz.user.service;

import checkvuz.checkvuz.user.entity.UserRole;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface RoleServiceInterface {

    CollectionModel<EntityModel<UserRole>> getRoles();

    ResponseEntity<EntityModel<UserRole>> createRole(UserRole roleToCreate);

    EntityModel<UserRole> getRoleById(Integer roleId);

    EntityModel<UserRole> getRoleByTitle(String roleTitle);

    ResponseEntity<EntityModel<UserRole>> updateRole(UserRole roleToUpdate, Integer roleId);

    ResponseEntity<?> deleteRole(Integer roleId);

    UserRole getUserRole();
}
