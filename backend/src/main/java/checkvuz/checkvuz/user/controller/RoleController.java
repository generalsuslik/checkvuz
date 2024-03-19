package checkvuz.checkvuz.user.controller;

import checkvuz.checkvuz.user.entity.UserRole;
import checkvuz.checkvuz.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("")
    public CollectionModel<EntityModel<UserRole>> getRoles() {
        return roleService.getRoles();
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<UserRole>> createRole(@RequestBody UserRole roleToCreate) {
        return roleService.createRole(roleToCreate);
    }

    @GetMapping("/{roleId}")
    public EntityModel<UserRole> getRoleById(@PathVariable Integer roleId) {
        return roleService.getRoleById(roleId);
    }

    @GetMapping("/{roleTitle}")
    public EntityModel<UserRole> getRoleByTitle(@PathVariable String roleTitle) {
        return roleService.getRoleByTitle(roleTitle);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<EntityModel<UserRole>> updateRole(@RequestBody UserRole roleToUpdate,
                                                        @PathVariable Integer roleId) {
        return roleService.updateRole(roleToUpdate, roleId);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer roleId) {
        return roleService.deleteRole(roleId);
    }
}
