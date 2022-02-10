package en.builin.qna.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserCreateDto {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    //TODO Имя не занято - сделать валидатор
    private String name;
    @NotBlank
    //TODO минимальная длина = 6
    private String password;
}
