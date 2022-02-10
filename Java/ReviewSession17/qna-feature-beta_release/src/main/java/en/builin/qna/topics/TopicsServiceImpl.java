/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.topics;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicsServiceImpl implements TopicsService {

    private final TopicsRepository topicsRepository;

    @Override
    public List<Topic> getTopics() {
        return topicsRepository.findAll(Sort.by("name"));
    }

    @Override
    public Topic getTopicById(Long id) {
        return topicsRepository.getById(id);
    }

    @Override
    public void saveTopic(Topic topic) {
        topicsRepository.save(topic);
    }

    @Override
    public void deleteTopic(Topic topic) {
        topicsRepository.delete(topic);
    }
}
