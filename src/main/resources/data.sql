-- Exams
INSERT INTO EXAM (name, description) VALUES ('Java Exam', 'Show your Java skills!');
INSERT INTO EXAM (name, description) VALUES ('Scala Exam', 'Are you functional?');

-- Java Questions with Answers
INSERT INTO QUESTION (exam_id, name, multi_answer) VALUES (1, 'Which collection cannot contain duplicate elements', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'Set', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'Map', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'List', 0);

INSERT INTO QUESTION (exam_id, name, multi_answer) VALUES (1, 'What are the basic interfaces of Java Collections Framework ?', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, 'Set', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, 'Random', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, 'List', 1);

INSERT INTO QUESTION (exam_id, name, multi_answer) VALUES (1, 'What’s a deadlock ?', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'A condition that occurs when two processes are waiting for each other to complete', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'Endless loop', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'Broken lock', 0);

-- Scala Questions with Answers
INSERT INTO QUESTION (exam_id, name, multi_answer) VALUES (2, 'How to write a for loop?', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'for (i <- 0 until 10) { ... }', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'for (int i=0; i < 10; i++) { ... }', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'Scala have no for loops', 0);

INSERT INTO QUESTION (exam_id, name, multi_answer) VALUES (2, 'What is Scala?', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'Programming language', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'Teatro La Scala, Milano, Italy', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'The Rock in Russia', 1);

INSERT INTO QUESTION (exam_id, name, multi_answer) VALUES (2, 'Can I use existing Java code from Scala?', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'Yes', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'No', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'Only starting from Java 8', 0);
