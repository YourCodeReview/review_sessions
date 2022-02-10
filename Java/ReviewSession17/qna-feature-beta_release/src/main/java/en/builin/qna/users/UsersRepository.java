package en.builin.qna.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}