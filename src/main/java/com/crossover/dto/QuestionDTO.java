package com.crossover.dto;

import com.crossover.entity.Question;

import java.io.Serializable;

public class QuestionDTO implements Serializable {
    public QuestionDTO() {
    }

    public QuestionDTO(Question question) {
        this(question.getId(), question.getName(), question.isMultiAnswer());
    }

    public QuestionDTO(Integer id, String name, boolean multiAnswer) {
        this.id = id;
        this.name = name;
        this.multiAnswer = multiAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private Integer id;
    private String name;
    private boolean multiAnswer = false;
}
