package checkvuz.checkvuz.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDto {

    private String username;
    private String email;
    private String password;
    private String confirmedPassword;
    private MultipartFile imageFile;
}
