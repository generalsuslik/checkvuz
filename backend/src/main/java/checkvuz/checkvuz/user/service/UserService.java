package checkvuz.checkvuz.user.service;

import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.user.assembler.UserModelAssembler;
import checkvuz.checkvuz.user.entity.User;
import checkvuz.checkvuz.user.entity.UserRole;
import checkvuz.checkvuz.user.exception.UserNotFoundByEmailException;
import checkvuz.checkvuz.user.exception.UserNotFoundByIdException;
import checkvuz.checkvuz.user.repository.UserRepository;
import checkvuz.checkvuz.utils.image.entity.Image;
import checkvuz.checkvuz.utils.image.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, UserServiceInterface {

    private final UserModelAssembler userModelAssembler;
    private final UserRepository userRepository;

    private final ImageService imageService;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User %s not found", username)
        ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getTitle())).collect(Collectors.toList())
        );
    }

    @Override
    public List<User> getUsers() {

        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    @Transactional
    public User createUser(@NonNull RegistrationUserDto registrationUserDto) {

        User user = User.builder()
                .username(registrationUserDto.getUsername())
                .email(registrationUserDto.getEmail())
                .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                .roles(Set.of(roleService.getUserRole()))
                .build();

        Image defaultUserImage = imageService.getDefaultImageData();
        user.setUserImage(defaultUserImage);

        return userRepository.save(user);
    }

    @Override
    public User addUserImage(Long userId, MultipartFile imageToAdd) throws IOException {

        User user = getUserById(userId);
        Image userImage = imageService.saveImageToStorage(imageToAdd, "/users/");

        user.setUserImage(userImage);
        return user;
    }

    @Override
    public User getUserById(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundByEmailException(email));
    }

    @Override
    public User updateUser(User userToUpdate, Long userId) {

        return userRepository.findById(userId)
                .map(user -> {
                    user.setId(userToUpdate.getId());
                    user.setUsername(userToUpdate.getUsername());
                    user.setEmail(userToUpdate.getEmail());
                    user.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
                    user.setUserImage(userToUpdate.getUserImage());
                    user.setRoles(userToUpdate.getRoles());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    userToUpdate.setId(userId);
                    return userRepository.save(userToUpdate);
                });
    }

    @Override
    public User assignUserRole(String role, Long userId) {

        UserRole userRole = roleService.getRoleByTitle(role).getContent();
        User updatedUser = getUserById(userId);
        updatedUser.getRoles().add(userRole);
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {

        userRepository.deleteById(userId);
    }

    @Override
    public EntityModel<User> convertUserToModel(User user) {

        return userModelAssembler.toModel(user);
    }
}
