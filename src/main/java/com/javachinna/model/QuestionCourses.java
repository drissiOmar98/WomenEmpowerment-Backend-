package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Getter
@Setter
@ToString
@Table( name = "QuestionCourses")
public class QuestionCourses {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quesId;
    private String title;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;

    private int ans;
    private int chose;

    public QuestionCourses() {
        super();
    }

    public QuestionCourses(int quesId, String title, String optionA, String optionB, String optionC,String optionD, int ans, int chose) {
        super();
        this.quesId = quesId;
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.ans = ans;
        this.chose = chose;
    }

    @ManyToOne
    @JsonIgnore
    private QuizCourses quiz;




}
