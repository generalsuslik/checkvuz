package checkvuz.checkvuz.user.service;

import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.user.assembler.UserModelAssembler;
import checkvuz.checkvuz.user.controller.UserController;
import checkvuz.checkvuz.user.entity.User;
import checkvuz.checkvuz.user.exception.UserNotFoundByEmailException;
import checkvuz.checkvuz.user.exception.UserNotFoundByIdException;
import checkvuz.checkvuz.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService, UserServiceInterface {

    private final UserModelAssembler userModelAssembler;
    private final UserRepository userRepository;

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
    public CollectionModel<EntityModel<User>> getUsers() {

        List<EntityModel<User>> userList = userRepository.findAll().stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(userList, linkTo(methodOn(UserController.class).getUsers()).withSelfRel());
    }

    @Override
    @Transactional
    public User createUser(@NonNull RegistrationUserDto registrationUserDto) {
        return userRepository.save(
                User.builder()
                        .username(registrationUserDto.getUsername())
                        .email(registrationUserDto.getEmail())
                        .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                        .roles(Set.of(roleService.getUserRole()))
                        .build()
        );
    }

    @Override
    public EntityModel<User> getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));

        return userModelAssembler.toModel(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public EntityModel<User> getUserByEmail(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundByEmailException(email));

        return userModelAssembler.toModel(user);
    }

    @Override
    public ResponseEntity<EntityModel<User>> updateUser(User userToUpdate, Long userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        return null;
    }
}
