package checkvuz.checkvuz.user.service;

import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.user.entity.User;
import org.springframework.hateoas.EntityModel;

import java.util.List;

public interface UserServiceInterface {

    List<User> getUsers();

    User createUser(RegistrationUserDto registrationUserDto);

    User getUserById(Long userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User updateUser(User userToUpdate, Long userId);

    User assignUserRole(String role, Long userId);

    void deleteUser(Long userId);

    EntityModel<User> convertUserToModel(User user);
}
