package en.builin.qna.questions;

import en.builin.qna.topics.Topic;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class QuestionCreateDto {

    @NotNull(message = "должно быть заполнено")
    // TODO Не работает валидатор
    private Topic topic;
    @NotBlank(message = "должно быть заполнено")
    private String text;
}
