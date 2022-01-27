/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.questions;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionsMapperImpl implements QuestionsMapper {

    private final ModelMapper modelMapper;

    public QuestionsMapperImpl() {
        modelMapper = new ModelMapper();
    }

    @Override
    public QuestionDto toDto(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }

    @Override
    public QuestionDto toDto(Question question, QuestionDto questionDto) {
        modelMapper.map(question, questionDto);
        return questionDto;
    }

    @Override
    public List<QuestionDto> toDto(List<Question> questions) {
        return questions.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Question fromDto(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }

    @Override
    public Question fromDto(QuestionDto questionDto, Question question) {
        modelMapper.map(questionDto, question);
        return question;
    }

    @Override
    public List<Question> fromDto(List<QuestionDto> questions) {
        return questions.stream().map(this::fromDto).collect(Collectors.toList());
    }

    @Override
    public Question fromDto(QuestionCreateDto questionCreateDto) {
        return modelMapper.map(questionCreateDto, Question.class);
    }
}
