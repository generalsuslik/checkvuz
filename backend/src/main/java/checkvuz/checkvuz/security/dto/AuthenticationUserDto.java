package checkvuz.checkvuz.security.dto;

import lombok.*;

@Data
@Builder
public class AuthenticationUserDto {

    private String username;
    private String password;
}
