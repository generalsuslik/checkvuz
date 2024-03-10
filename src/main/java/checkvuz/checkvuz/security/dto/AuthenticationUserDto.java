package checkvuz.checkvuz.security.dto;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationUserDto {

    private String username;
    private String password;
}
