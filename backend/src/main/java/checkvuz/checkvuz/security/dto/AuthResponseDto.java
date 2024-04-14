package checkvuz.checkvuz.security.dto;

import lombok.*;

@Data
@Builder
public class AuthResponseDto {

    private String username;
    private String token;
}
