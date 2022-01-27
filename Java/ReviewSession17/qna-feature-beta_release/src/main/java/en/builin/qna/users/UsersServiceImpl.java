package en.builin.qna.users;

import en.builin.qna.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public User getUserByEmail(String email) {
        return usersRepository.findById(email).orElseThrow(UserNotFoundException::new);
    }
}
