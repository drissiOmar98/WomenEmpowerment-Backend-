package com.javachinna.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionForm {

    private List<QuestionCourses> questions;

    public List<QuestionCourses> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCourses> questions) {

        this.questions = questions;

    }
}
