package en.builin.qna.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Пользователь не найден")
public class UserNotFoundException extends RuntimeException {
//    public UserNotFoundException() {
//        super("Пользователь не найден");
//    }
}
