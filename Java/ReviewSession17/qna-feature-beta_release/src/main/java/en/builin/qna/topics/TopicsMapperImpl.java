/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.topics;

import en.builin.qna.utils.WebUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicsMapperImpl implements TopicsMapper {

    private final ModelMapper modelMapper;

    public TopicsMapperImpl() {
        modelMapper = new ModelMapper();
        modelMapper.typeMap(TopicDto.class, Topic.class).addMappings(mapping -> {
            mapping.skip(Topic::setCreatedAt);
            mapping.skip(Topic::setUpdatedAt);
        });
    }

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto topicDto = modelMapper.map(topic, TopicDto.class);
        additionalMapping(topic, topicDto);
        return topicDto;
    }

    @Override
    public TopicDto toDto(Topic topic, TopicDto topicDto) {
        modelMapper.map(topic, topicDto);
        additionalMapping(topic, topicDto);
        return topicDto;
    }

    @Override
    public List<TopicDto> toDto(List<Topic> topics) {
        return topics.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Topic fromDto(TopicDto topicDto) {
        return modelMapper.map(topicDto, Topic.class);
    }

    @Override
    public void fromDto(TopicDto topicDto, Topic topic) {
        modelMapper.map(topicDto, topic);
    }

    @Override
    public List<Topic> fromDto(List<TopicDto> topicDtos) {
        return topicDtos.stream().map(this::fromDto).collect(Collectors.toList());
    }

    private void additionalMapping(Topic from, TopicDto to) {
        to.setUrl(WebUtils.getUrlTitleById(from.getName(), from.getId()));
    }
}
