package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table( name = "QuizCourses")
public class QuizCourses  implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idQuiz;

    private String title;

    private Integer score;

    @Temporal (TemporalType.DATE)
    private Date createAt;

    private String content ;

    @ManyToOne
    @JsonIgnore
    private Formation formation;

    @OneToMany(mappedBy = "quiz",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<QuestionCourses> question;

    @OneToMany(mappedBy = "quiz",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<Result> results;

}
