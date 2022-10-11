package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table( name = "Result")
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private int totalCorrect = 0;
    private int correctAnswer = 0;
    private int inCorrectAnswer = 0;
    private boolean status;

    public Result() {
        super();
    }

    public Result(int id, String username, int totalCorrect, int correctAnswer, int inCorrectAnswer ,boolean status) {
        super();
        this.id = id;
        this.username = username;
        this.totalCorrect = totalCorrect;
        this.correctAnswer = correctAnswer;
        this.inCorrectAnswer = inCorrectAnswer;
        this.status = status;
    }

    @ManyToOne
    @JsonIgnore
    private QuizCourses quiz;


    @ManyToOne
    @JsonIgnore
    private User sUser;

}
