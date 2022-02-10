package en.builin.qna;

import en.builin.qna.questions.QuestionsController;
import en.builin.qna.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(WebUtils.URL_INDEX)
@RequiredArgsConstructor()
public class IndexController {

    private final QuestionsController questionsController;

    @GetMapping
    public String getIndexPage(@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model) {

        return questionsController.showQuestionsPage(pageNumber, model);
    }
}