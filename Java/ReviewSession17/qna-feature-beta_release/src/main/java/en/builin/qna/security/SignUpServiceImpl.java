package en.builin.qna.security;

import en.builin.qna.exceptions.UserAlreadyRegisteredException;
import en.builin.qna.users.User;
import en.builin.qna.users.UserCreateDto;
import en.builin.qna.users.UsersRepository;
import en.builin.qna.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;

    @Override
    public void signUp(UserCreateDto dto) {

        if (usersRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyRegisteredException();
        }

        User user = ModelMapperUtils.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setState(User.State.NOT_CONFIRMED);
        user.setRole(User.Role.USER);

        usersRepository.save(user);

//        emailService.sendEmail(user.getEmail(), EmailResolver.REGISTERED_SUBJECT, EmailResolver.REGISTERED_BODY);
    }
}
