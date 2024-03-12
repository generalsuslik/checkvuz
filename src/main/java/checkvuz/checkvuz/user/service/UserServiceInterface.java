package checkvuz.checkvuz.user.service;

import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.user.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserServiceInterface {

    CollectionModel<EntityModel<User>> getUsers();

    User createUser(RegistrationUserDto registrationUserDto);

    EntityModel<User> getUserById(Long userId);

    Optional<User> getUserByUsername(String username);

    EntityModel<User> getUserByEmail(String email);

    ResponseEntity<EntityModel<User>> updateUser(User userToUpdate, Long userId);

    ResponseEntity<?> assignUserRole(String role, Long userId);

    ResponseEntity<?> deleteUser(Long userId);
}
