package com.crossover;


import com.crossover.entity.Exam;
import com.crossover.service.ExamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Profile("test")
@TestPropertySource(locations = "classpath:/application-test.properties", inheritProperties = false)
@SpringApplicationConfiguration(classes = {JpaConfig.class, Application.class})
public class ServiceTest {
    @Autowired
    ExamService svc;

    @Test
    public void testExamService() {
        for (int i = 0; i < 10; i++) {
            final Exam ex = svc.getRandomExam();
            assertNotNull(ex);
            assertNotNull(ex.getName());
            assertNotNull(ex.getDescription());
            assertTrue(ex.getName().contains("Java") || ex.getName().contains("Scala"));
            logger.info(ex.getDescription());
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);
}
