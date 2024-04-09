package checkvuz.checkvuz.security.controller;

import checkvuz.checkvuz.security.dto.AuthResponseDto;
import checkvuz.checkvuz.security.dto.AuthenticationUserDto;
import checkvuz.checkvuz.security.dto.RegistrationUserDto;
import checkvuz.checkvuz.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegistrationUserDto request) throws IOException {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthenticationUserDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
