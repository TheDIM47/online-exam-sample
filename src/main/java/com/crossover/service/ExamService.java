package com.crossover.service;

import com.crossover.entity.Answer;
import com.crossover.entity.Exam;
import com.crossover.entity.ExamProtocol;
import com.crossover.entity.Question;
import com.crossover.repository.AnswerRepository;
import com.crossover.repository.ExamProtocolRepository;
import com.crossover.repository.ExamRepository;
import com.crossover.repository.QuestionRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamService {
    public static final double MAX_GRADE = 100; // 100 points

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private ExamProtocolRepository protocolRepo;

    public Exam getRandomExam() {
        final int examCount = (int) examRepo.count();
        assert examCount > 0;
        final int examNumber = new Random().nextInt(examCount);
        return examRepo.findAll().get(examNumber);
    }

    @Transactional
    public int insertExam(ExamProtocol protocol) {
        final ExamProtocol p = protocolRepo.save(protocol);
        protocolRepo.flush();
        return protocol.getId();
    }

    @Transactional
    public ExamProtocol updateExamProtocol(ExamProtocol protocol) {
        return protocolRepo.save(protocol);
    }

    public Exam getExam(int examId) {
        final Exam exam = examRepo.findOne(examId);
        return exam;
    }

    public ExamProtocol getExamProtocol(int protocolId) {
        return protocolRepo.findOne(protocolId);
    }


    public List<Question> getQuestionsForExam(int examId) {
        final List<Question> questions = questionRepo.findByExamId(examId);
        return questions;
//        return questions.stream().map(a -> new ImmutablePair<>(a.getId(), a.getName())).collect(Collectors.toList());
    }

    public List<Answer> getAnswersForQuestion(int questionId) {
        final List<Answer> answers = answerRepo.findByQuestionId(questionId);
        return answers;
//        return answers.stream().map(a -> new ImmutablePair<>(a.getId(), a.getName())).collect(Collectors.toList());
    }

    public Question getNextQuestion(Exam exam, Set<Integer> ids) {
        final Set<Integer> identificators = new HashSet<>(ids);
        if (identificators.size() == 0) {
            identificators.add(0); // add to avoid null
        }
        return questionRepo.findFirstByExamAndIdNotIn(exam, identificators);
    }

    /**
     * Process grade user answer on some question
     *
     * @param protocol user exam protocol
     * @param examId exam ID
     * @param userAnswers user answers as array of answer IDs
     */
    public void calcGrade(ExamProtocol protocol, int examId, List<Integer> userAnswers) {
        if (protocol == null || userAnswers == null) {
            throw new IllegalArgumentException("Invalid parameters on GRADE call");
        }

        final List<Answer> correctAnswers = answerRepo.findByQuestionExamIdAndCorrect(examId, true);
        if (correctAnswers.size() == 0) {
            throw new IllegalArgumentException("You must specify correct answers!");
        }

        final float step = (float) (MAX_GRADE / correctAnswers.size());
        final Set<Integer> correctCount = new HashSet<>();
        final Set<Integer> incorrectCount = new HashSet<>();

        final Set<Integer> answers = userAnswers.stream().filter(a -> a > 0).collect(Collectors.toSet());

        float grade = 0;
        for (final Answer answer : correctAnswers) {
            final Integer id = answer.getId();
            if (answers.contains(id)) {
                grade += step;
                correctCount.add(id);
            } else {
                incorrectCount.add(id);
            }
        }
        // fix for multi-answers questions
        correctCount.removeAll(incorrectCount);

        // grade
        protocol.setCorrectAnswers(correctCount.size());
        protocol.setGrade(Math.round(grade));
    }


    /**
     * Process grade user answer on some question
     *
     * @param protocol user exam protocol
     * @param answers set of answers for that question
     * @param solutions user answers as array of answer IDs
     * @return user exam protocol
     */
    /*
    public static ExamProtocol grade(ExamProtocol protocol, List<Answer> answers, List<Integer> solutions) {
        if (protocol == null || answers == null || solutions == null) {
            throw new IllegalArgumentException("Invalid parameters on GRADE call");
        }

        final Set<Integer> correctAnswers = answers.stream()
                .filter(Answer::isCorrect)
                .map(AbstractPersistable::getId)
                .collect(Collectors.toSet());

        if (correctAnswers.size() == 0) {
            throw new IllegalArgumentException("You must specify correct answers!");
        }

        final Set<Integer> userAnswers = solutions.stream()
                .filter(a -> a > 0)
                .collect(Collectors.toSet());

        float step = (float) (MAX_GRADE / correctAnswers.size());
        float grade = 0;

        for (final Integer userAnswer : userAnswers) {
            if (correctAnswers.contains(userAnswer)) {
                grade += step;
            }
        }

        protocol.setAnswered(1 + protocol.getAnswered());
        if (Math.round(grade) == MAX_GRADE) {
            protocol.setCorrectAnswers(1 + protocol.getCorrectAnswers());
        }

        return protocol;
    }
    */

    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

}
