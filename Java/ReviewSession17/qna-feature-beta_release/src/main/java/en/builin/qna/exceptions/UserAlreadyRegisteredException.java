package en.builin.qna.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "Пользователь с таким email уже зарегистрирован. Воспользуйтесь восстановлением пароля.")
public class UserAlreadyRegisteredException extends RuntimeException {
//    public UserAlreadyRegisteredException() {
//        super("Пользователь с таким email уже зарегистрирован. Воспользуйтесь восстановлением пароля.");
//    }
}
