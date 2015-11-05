package com.crossover;

import com.crossover.entity.*;
import com.crossover.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@TestPropertySource(locations = "classpath:/application-test.properties", inheritProperties = false)
@SpringApplicationConfiguration(classes = {JpaConfig.class, Application.class})
public class RepositoryTest {

    @Autowired
    private ExamRepository examRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ExamProtocolRepository protocolRepo;

    @Test
    public void findsAllExams() {
        final List<Exam> list = examRepo.findAll();
        assertEquals(MAX_EXAMS, list.size());
        for (final Exam item : list) {
            assertNotNull(item.getName());
            assertTrue(item.getName().length() > 0);
        }
    }

    @Test
    public void findsAllQuestions() {
        final List<Question> list = questionRepo.findAll();
        assertEquals(MAX_QUESTIONS, list.size());
        for (final Question item : list) {
            assertNotNull(item.getName());
            assertTrue(item.getName().length() > 0);
        }
    }

    @Test
    public void findsAllAnswers() {
        final List<Answer> list = answerRepo.findAll();
        assertEquals(MAX_ANSWERS, list.size());
        for (final Answer item : list) {
            assertNotNull(item.getName());
            assertTrue(item.getName().length() > 0);
        }
    }

    @Test
    public void findsAllUsers() {
        final List<User> list = userRepo.findAll();
        assertTrue(list.size() > 1);
        for (final User item : list) {
            assertNotNull(item.getUsername());
            assertTrue(item.getUsername().length() > 0);
        }
    }

    @Test
    public void findsAllAnswersByExam() {
        final List<Answer> listValid = answerRepo.findByQuestionExamIdAndCorrect(1, true);
        assertNotNull(listValid);
        final List<Answer> listInvalid = answerRepo.findByQuestionExamIdAndCorrect(1, false);
        assertNotNull(listInvalid);
        assertEquals(MAX_QUESTIONS_BY_EXAM * MAX_ANSWERS_BY_QUESTION, listValid.size() + listInvalid.size());
    }

    @Test
    @Transactional
    public void testInsertExam() {
        final User user = new User();
        user.setUsername("zzz");
        user.setPassword("qqq");
        final User u = userRepo.save(user);
        userRepo.flush();
        final List<Exam> exams = examRepo.findAll();
        assertEquals(MAX_EXAMS, exams.size());
        final List<Question> questions = questionRepo.findByExam(exams.get(0));
        assertEquals(MAX_QUESTIONS_BY_EXAM, questions.size());
        final ExamProtocol protocol = new ExamProtocol();
        protocol.setExam(exams.get(0));
        protocol.setUser(user);
        protocol.setStart(Calendar.getInstance().getTime());
        protocol.setQuestionCount(questions.size());
        final ExamProtocol p = protocolRepo.save(protocol);
        protocolRepo.flush();
    }

    @Test
    public void findsAllExamsQuestionsAnswers() {
        final List<Exam> exams = examRepo.findAll();
        assertEquals(MAX_EXAMS, exams.size());
        for (final Exam exam : exams) {
            final List<Question> questions = questionRepo.findByExam(exam);
            assertEquals(MAX_QUESTIONS_BY_EXAM, questions.size());
            for (final Question question : questions) {
                final List<Answer> answers = answerRepo.findByQuestion(question);
                assertEquals(MAX_ANSWERS_BY_QUESTION, answers.size());
            }
        }
    }

    private static final int MAX_EXAMS = 2;
    private static final int MAX_QUESTIONS = 6;
    private static final int MAX_QUESTIONS_BY_EXAM = 3;
    private static final int MAX_ANSWERS = 18;
    private static final int MAX_ANSWERS_BY_QUESTION = 3;
    private static final int MAX_USERS = 5;

    private static final Logger logger = LoggerFactory.getLogger(RepositoryTest.class);
}