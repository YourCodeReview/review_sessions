package en.builin.qna.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

    static final Long serialVersionUID = 1L;

    public enum Role {
        USER, MODERATOR, ADMIN
    }

    public enum State {
        NOT_REGISTERED, NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(unique = true)
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private State state;
    private String name;
    private String password;
}
