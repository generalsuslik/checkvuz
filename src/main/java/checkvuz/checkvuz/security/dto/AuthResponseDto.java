package checkvuz.checkvuz.security.dto;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    private String username;
    private String token;
}
