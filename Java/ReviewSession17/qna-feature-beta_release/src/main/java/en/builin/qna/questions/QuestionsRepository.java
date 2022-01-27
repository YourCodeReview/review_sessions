package en.builin.qna.questions;

import en.builin.qna.topics.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Question, Long> {

    List<Question> findByDeletedIsNullOrderByCreatedAtDesc(Pageable pageable);

    List<Question> findByTopicAndDeletedIsNullOrderByCreatedAtDesc(Topic topic, Pageable pageable);

    List<Question> findByDeletedIsNull(Sort sort);

    Long countByDeletedIsNull();
}