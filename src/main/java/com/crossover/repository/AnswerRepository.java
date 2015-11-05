package com.crossover.repository;

import com.crossover.entity.Answer;
import com.crossover.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestion(Question question);

    List<Answer> findByQuestionId(Integer questionId);

    List<Answer> findByQuestionExamIdAndCorrect(Integer examId, Boolean isCorrect);
}
