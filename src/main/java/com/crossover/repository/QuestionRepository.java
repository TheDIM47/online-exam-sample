package com.crossover.repository;

import com.crossover.entity.Exam;
import com.crossover.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByExam(Exam exam);

    List<Question> findByExamId(Integer examId);

    // find first not answered question by exam
    Question findFirstByExamAndIdNotIn(Exam exam, Set<Integer> ids);

    int countByExam(Exam exam);
}
