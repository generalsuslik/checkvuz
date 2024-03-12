package checkvuz.checkvuz.user.controller;

import checkvuz.checkvuz.user.entity.User;
import checkvuz.checkvuz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public CollectionModel<EntityModel<User>> getUsers() {

        List<User> users = userService.getUsers();
        List<EntityModel<User>> userModels = users.stream()
                .map(userService::convertUserToModel)
                .collect(Collectors.toList());

        return CollectionModel.of(userModels,
                linkTo(methodOn(UserController.class).getUsers()).withSelfRel());
    }

    @GetMapping("/{userId}")
    public EntityModel<User> getUserById(@PathVariable Long userId) {

        return userService.convertUserToModel(
                userService.getUserById(userId)
        );
    }

    @GetMapping("/{username}")
    public EntityModel<User> getUserByUsername(@PathVariable String username) {

        return userService.convertUserToModel(
                userService.getUserByUsername(username)
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EntityModel<User>> updateUser(@RequestBody User userToUpdate,
                                                        @PathVariable Long userId) {

        EntityModel<User> entityModel = userService.convertUserToModel(
                userService.updateUser(userToUpdate, userId)
        );

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
