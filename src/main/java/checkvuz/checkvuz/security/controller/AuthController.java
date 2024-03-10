package checkvuz.checkvuz.security.controller;

import checkvuz.checkvuz.security.dto.AuthResponseDto;
import checkvuz.checkvuz.security.dto.AuthenticationUserDto;
import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("sign-up")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegistrationUserDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthenticationUserDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
