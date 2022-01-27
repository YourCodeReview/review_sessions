package en.builin.qna.topics;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "topics")
@Entity
@Data
@NoArgsConstructor
public class Topic {

    // TODO сделать классы DTO, repository, service

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long id;
    @Column(length = 150)
    private String name;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
