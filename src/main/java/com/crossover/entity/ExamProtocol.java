package com.crossover.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROTOCOL")
public class ExamProtocol extends AbstractPersistable<Integer> {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ExamProtocol{" +
                "user=" + user +
                ", exam=" + exam +
                ", start=" + start +
                ", finish=" + finish +
                ", questionCount=" + questionCount +
                ", correctAnswers=" + correctAnswers +
                ", grade=" + grade +
                '}';
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date finish;

    @Column(name = "QUESTION_COUNT")
    private Integer questionCount;

    @Column(name = "CORRECT_ANSWERS")
    private Integer correctAnswers;

    @Column
    private Integer grade;
}
