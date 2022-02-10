package en.builin.qna.security;

import en.builin.qna.users.UserCreateDto;

public interface SignUpService {

    void signUp(UserCreateDto dto);
}
