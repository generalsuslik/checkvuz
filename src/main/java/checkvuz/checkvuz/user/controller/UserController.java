package checkvuz.checkvuz.user.controller;

import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.user.entity.User;
import checkvuz.checkvuz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public CollectionModel<EntityModel<User>> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody RegistrationUserDto registrationUserDto) {
        User createdUser = userService.createUser(registrationUserDto);
        EntityModel<User> entityModel = userService.getUserById(createdUser.getId());
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{userId}")
    public EntityModel<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

//    @GetMapping("/{username}")
//    public EntityModel<User> getUserByUsername(@PathVariable String username) {
//        return userService.getUserById();
//    }

    @PutMapping("/{userId}")
    public ResponseEntity<EntityModel<User>> updateUser(@RequestBody User userToUpdate,
                                                        @PathVariable Long userId) {

        return userService.updateUser(userToUpdate, userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
