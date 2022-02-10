package en.builin.qna;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class QnaApplication {

    //TODO сделать страницу логина, в которой будут выводится ошибки входа и на которую будет выбрасывать при попытках входа: /login?error

    //TODO сделать, чтобы при логине со страницы пользователь оставался на текущей странице

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Thymeleaf Layout Dialect Bean https://ultraq.github.io/thymeleaf-layout-dialect/getting-started/
     */
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(QnaApplication.class, args);
    }

}
