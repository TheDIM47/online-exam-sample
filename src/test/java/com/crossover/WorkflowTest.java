package com.crossover;

import com.crossover.entity.*;
import com.crossover.repository.AnswerRepository;
import com.crossover.repository.ExamProtocolRepository;
import com.crossover.repository.QuestionRepository;
import com.crossover.repository.UserRepository;
import com.crossover.service.ExamService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {JpaConfig.class, Application.class})
//@TestPropertySource(locations = "classpath:application-test.properties")
public class WorkflowTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ExamProtocolRepository protocolRepo;

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private AnswerRepository answerRepo;

    @Autowired
    private ExamService examSvc;

    @Ignore
    @Deprecated
    public void workflowTest() {
        assertNotNull(userRepo);
        assertNotNull(examSvc);

        // get user
        final User student = userRepo.findOne(2);
        assertNotNull(student);
        assertEquals(student.getId().intValue(), 2);

        // get random exam
        final Exam exam = examSvc.getRandomExam();

        // create new exam session
        final ExamProtocol protocol = new ExamProtocol();
        assertNotNull(protocol);
        // init exam session
        protocol.setExam(exam);
        protocol.setUser(student);
        protocol.setStart(Calendar.getInstance().getTime());
        protocol.setQuestionCount(questionRepo.countByExam(exam));


        int terminator = 0;
//        Question q;
//        while ((q = examSvc.getNextQuestion(exam, protocol.getPassed())) != null) {
//            final List<Answer> allowedAnswers = answerRepo.findByQuestion(q);
//            assertNotNull(allowedAnswers);
//            assertTrue(allowedAnswers.size() > 0);
//
//            // set random answer(s)
//            final List<Integer> userAnswers = allowedAnswers.stream()
//                    .map(AbstractPersistable::getId)
//                    .filter(a -> (Math.random() > 0.5))
//                    .collect(Collectors.toList());
//
//            ExamService.grade(protocol, allowedAnswers, userAnswers);
//
//            protocol.getPassed().add(q.getId());
//            if (terminator++ > 100) {
//                fail("Illegal question count!");
//            }
//        }

        protocol.setFinish(Calendar.getInstance().getTime());

        // exam finished
        protocolRepo.save(protocol);
        assertNotNull(protocol);
        logger.info("RESULTS FOR EXAM: {} [{}]", exam.getName(), exam.getDescription());
        logger.info("RESULT: Started:  {}", protocol.getStart());
        logger.info("RESULT: Finished: {}", protocol.getFinish());
        logger.info("RESULT: Total:    {}", protocol.getQuestionCount());
//        logger.info("RESULT: Answered: {}", protocol.getAnswered());
        logger.info("RESULT: Correct:  {}", protocol.getCorrectAnswers());
        logger.info("RESULT: GRADE:    {} [{} of {}]", protocol.getCorrectAnswers() / protocol.getQuestionCount(),
                protocol.getCorrectAnswers(), protocol.getQuestionCount());
    }

    private static final Logger logger = LoggerFactory.getLogger(WorkflowTest.class);
}
