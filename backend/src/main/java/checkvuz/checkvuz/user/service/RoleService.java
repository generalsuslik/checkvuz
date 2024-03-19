package checkvuz.checkvuz.user.service;

import checkvuz.checkvuz.user.assembler.RoleModelAssembler;
import checkvuz.checkvuz.user.controller.RoleController;
import checkvuz.checkvuz.user.entity.UserRole;
import checkvuz.checkvuz.user.exception.RoleNotFoundByIdException;
import checkvuz.checkvuz.user.exception.RoleNotFoundByTitleException;
import checkvuz.checkvuz.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleServiceInterface {

    private final RoleModelAssembler roleModelAssembler;
    private final RoleRepository roleRepository;

    @Override
    public CollectionModel<EntityModel<UserRole>> getRoles() {

        List<EntityModel<UserRole>> roleList = roleRepository.findAll().stream()
                .map(roleModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roleList, linkTo(methodOn(RoleController.class).getRoles()).withSelfRel());
    }

    @Override
    public ResponseEntity<EntityModel<UserRole>> createRole(UserRole roleToCreate) {
        return null;
    }

    @Override
    public EntityModel<UserRole> getRoleById(Integer roleId) {

        UserRole role = roleRepository
                .findById(roleId).orElseThrow(() -> new RoleNotFoundByIdException(roleId));

        return roleModelAssembler.toModel(role);
    }

    @Override
    public EntityModel<UserRole> getRoleByTitle(String roleTitle) {
        UserRole role = roleRepository
                .findByTitle(roleTitle).orElseThrow(() -> new RoleNotFoundByTitleException(roleTitle));

        return roleModelAssembler.toModel(role);
    }

    @Override
    public ResponseEntity<EntityModel<UserRole>> updateRole(UserRole roleToUpdate, Integer roleId) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteRole(Integer roleId) {
        return null;
    }

    @Override
    public UserRole getUserRole() {
        return roleRepository.findByTitle("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundByTitleException("ROLE_USER"));
    }


}
