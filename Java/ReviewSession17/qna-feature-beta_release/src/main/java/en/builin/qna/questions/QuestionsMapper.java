/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.questions;

import java.util.List;

public interface QuestionsMapper {

    QuestionDto toDto(Question question);

    QuestionDto toDto(Question question, QuestionDto questionDto);

    List<QuestionDto> toDto(List<Question> questions);

    Question fromDto(QuestionDto questionDto);

    Question fromDto(QuestionDto questionDto, Question question);

    List<Question> fromDto(List<QuestionDto> questions);

    Question fromDto(QuestionCreateDto questionCreateDto);
}
