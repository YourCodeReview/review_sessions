package en.builin.qna.questions;

import en.builin.qna.topics.TopicDto;
import en.builin.qna.users.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class QuestionDto {

    private Long id;
    private UserDto author;
    private TopicDto topic;
    private String text;
    private Instant createdAt;
    private Instant updatedAt;
}
