package com.crossover.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * N-th Exam Question
 * <p/>
 * Question may have one (multiAnswer=false) or many correct answers (multiAnswer=true)
 */
@Entity
@Table(name = "QUESTION")
public class Question extends AbstractPersistable<Integer> {

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMultiAnswer() {
        return multiAnswer;
    }

    public void setMultiAnswer(boolean multiAnswer) {
        this.multiAnswer = multiAnswer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;
    @Column
    private String name;
    @Column(name = "MULTI_ANSWER", nullable = false)
    private boolean multiAnswer = false;
}
