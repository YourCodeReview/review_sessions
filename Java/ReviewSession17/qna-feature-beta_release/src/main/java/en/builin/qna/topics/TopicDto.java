package en.builin.qna.topics;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class TopicDto {

    private Long id;
    private String name;
    //    @DateTimeFormat(pattern="dd.MM.YYYY HH:mm")
    // TODO DateTimeFormat для Instant
    private Instant createdAt;
    private Instant updatedAt;
    private String url;
}
