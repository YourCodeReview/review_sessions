package en.builin.qna.users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private User.Role role;
    private User.State state;
}
