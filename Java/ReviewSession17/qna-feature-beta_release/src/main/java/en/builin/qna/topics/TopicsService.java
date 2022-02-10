/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.topics;

import java.util.List;

public interface TopicsService {

    List<Topic> getTopics();

    Topic getTopicById(Long id);

    void saveTopic(Topic topic);

    void deleteTopic(Topic topic);
}
