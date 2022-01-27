/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.topics;

import en.builin.qna.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(WebUtils.URL_EDIT_TOPICS)
public class TopicsController {

    private final TopicsService topicsService;
    private final TopicsMapper topicsMapper;

    @GetMapping
    public String getTopicsPage(Model model) {
        model.addAttribute("topicsDto", topicsMapper.toDto(topicsService.getTopics()));
        return "topics";
    }

    @GetMapping("/del")
    public String deleteTopic(@RequestParam("id") Long id) {
        topicsService.deleteTopic(topicsService.getTopicById(id));
        return "redirect:" + WebUtils.URL_TOPICS;
    }

    @PostMapping
    public String saveTopic(TopicDto topicDto) {
        if (topicDto.getId() != null) {
            Topic topic = topicsService.getTopicById(topicDto.getId());
            topicsMapper.fromDto(topicDto, topic);
            topicsService.saveTopic(topic);
        } else {
            topicsService.saveTopic(topicsMapper.fromDto(topicDto));
        }
        return "redirect:" + WebUtils.URL_TOPICS;
    }
}
