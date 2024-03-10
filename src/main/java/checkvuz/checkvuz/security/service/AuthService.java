package checkvuz.checkvuz.security.service;

import checkvuz.checkvuz.security.dto.AuthResponseDto;
import checkvuz.checkvuz.security.dto.AuthenticationUserDto;
import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.security.utils.JwtTokenUtils;
import checkvuz.checkvuz.user.entity.User;
import checkvuz.checkvuz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    private final UserService userService;

    public AuthResponseDto register(RegistrationUserDto request) {
        User user = userService.createUser(request);
        String jwtToken = jwtTokenUtils.generateToken(user);
        log.info(user.getRoles().toString());
        return AuthResponseDto.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .build();
    }

    public AuthResponseDto authenticate(AuthenticationUserDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String jwtToken = jwtTokenUtils.generateToken(userDetails);
        return AuthResponseDto.builder()
                .username(userDetails.getUsername())
                .token(jwtToken)
                .build();
    }

}
