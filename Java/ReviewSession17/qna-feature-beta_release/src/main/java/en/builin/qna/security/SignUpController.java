package en.builin.qna.security;

import en.builin.qna.users.UserCreateDto;
import en.builin.qna.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(WebUtils.URL_SIGN_UP)
public class SignUpController {

    private final SignUpService signUpService;

    @GetMapping
    public String getSignUpPage(Authentication authentication, Model model) {

        if (authentication != null) {
            return "redirect:" + WebUtils.URL_PROFILE;
        }

        model.addAttribute("userCreateDto", new UserCreateDto());
        return "sign-up";
    }

    @PostMapping
    public String signUp(@Valid UserCreateDto userCreateDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            //TODO сделать оформление ошибок заполнения в макете sign-up.html
            return "sign-up";
        }

        signUpService.signUp(userCreateDto);
        //TODO сделать автологин после регистрации
        return "redirect:" + WebUtils.URL_INDEX;
    }

}
