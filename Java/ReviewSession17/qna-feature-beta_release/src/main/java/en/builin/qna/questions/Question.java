package en.builin.qna.questions;

import en.builin.qna.topics.Topic;
import en.builin.qna.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "questions")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name="topic_id", nullable = false)
    private Topic topic;
    @Column(length = 1024)
    private String text;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
    private Boolean deleted;
}
