/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.questions;

import en.builin.qna.security.config.UserDetailsImpl;
import en.builin.qna.topics.TopicsMapper;
import en.builin.qna.topics.TopicsService;
import en.builin.qna.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class QuestionsController {

    private final QuestionsService questionsService;
    private final QuestionsMapper questionsMapper;
    private final TopicsService topicsService;
    private final TopicsMapper topicsMapper;

    @PostMapping(WebUtils.URL_QUESTION_PAGE)
    public String addQuestion(Authentication authentication,
                              @Valid QuestionCreateDto questionCreateDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("topicsDto", topicsMapper.toDto(topicsService.getTopics()));
            return "add-question";
        }

        Question question = questionsMapper.fromDto(questionCreateDto);
        question.setAuthor(((UserDetailsImpl) authentication.getPrincipal()).getUser());
        questionsService.addQuestion(question);

        return "redirect:" + WebUtils.URL_QUESTION_PAGE + "/" + questionsService.getQuestionUrlName(question);
    }

    @GetMapping(WebUtils.URL_ADD_QUESTION)
    public String showAddQuestionPage(Model model) {

        model.addAttribute("questionCreateDto", new QuestionCreateDto());
        model.addAttribute("topicsDto", topicsMapper.toDto(topicsService.getTopics()));
        return "add-question";
    }

    @GetMapping(WebUtils.URL_QUESTIONS)
    public String showQuestionsPage(@RequestParam(value = "page", defaultValue = "1") int pageNumber, Model model) {

        model.addAttribute("topicsDto", topicsMapper.toDto(topicsService.getTopics()));
        model.addAttribute("questionsDto",
                questionsMapper.toDto(questionsService.findQuestionsByPage(pageNumber)));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageTotal", questionsService.getPagesTotalCount());

        return "questions";
    }

    @GetMapping(WebUtils.URL_TOPICS + "/{topicUrlId}")
    public String showQuestionsPageByTopic(@PathVariable String topicUrlId,
                                           @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                                           Model model) {

        model.addAttribute("topicsDto", topicsMapper.toDto(topicsService.getTopics()));
        model.addAttribute("questionsDto",
                questionsMapper.toDto(
                        questionsService.findQuestionsByTopicAndPage(
                                topicsService.getTopicById(WebUtils.getIdFromUrl(topicUrlId)),
                                pageNumber)));
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageTotal", questionsService.getPagesTotalCount());

        return "questions";
    }
}
