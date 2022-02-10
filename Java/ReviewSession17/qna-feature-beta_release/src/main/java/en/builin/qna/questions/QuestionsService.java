/*
 * Copyright (c) 2022. Evgeniy Buylin
 */

package en.builin.qna.questions;

import en.builin.qna.topics.Topic;

import java.util.List;

public interface QuestionsService {

    void addQuestion(Question question);

    List<Question> findQuestionsByPage(int pageNumber);

    List<Question> findQuestionsByTopicAndPage(Topic topic, int pageNumber);

    List<Question> findQuestionsByAll();

    String getQuestionUrlName(Question question);

    Integer getPagesTotalCount();
}
