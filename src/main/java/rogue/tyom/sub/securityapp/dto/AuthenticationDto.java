package rogue.tyom.sub.securityapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthenticationDto {

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 5, max = 100, message = "Имя должно быть в пределах 5-100 символов")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

}
