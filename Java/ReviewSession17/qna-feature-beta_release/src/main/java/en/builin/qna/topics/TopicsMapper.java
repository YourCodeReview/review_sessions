/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.topics;

import java.util.List;

public interface TopicsMapper {

    TopicDto toDto(Topic topic);

    TopicDto toDto(Topic topic, TopicDto topicDto);

    List<TopicDto> toDto(List<Topic> topics);

    Topic fromDto(TopicDto topicDto);

    void fromDto(TopicDto topicDto, Topic topic);

    List<Topic> fromDto(List<TopicDto> topicDtos);
}
