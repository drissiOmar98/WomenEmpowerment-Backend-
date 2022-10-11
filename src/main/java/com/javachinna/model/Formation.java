package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "Courses")
public class Formation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idFormation;
    private String title;
    @Enumerated(EnumType.STRING)
    private Level level;
    @Temporal (TemporalType.TIMESTAMP)
    private Date start;
    @Temporal (TemporalType.TIMESTAMP)
    private Date end;
    private Integer nbrHeures;
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Max(30)
    @Min(0)
    private Integer nbrMaxParticipant;
    private Integer frais;

    @Max(5)
    @Min(0)
    private Double Rating;

    @ManyToOne
    @JsonIgnore
    private User formateur;

    @ManyToMany
    @JsonIgnore
    private Set<User> apprenant ;

    @OneToMany(mappedBy = "formation" ,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<QuizCourses> quizzes;


    @OneToMany(mappedBy = "formation",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<DatabaseFile> databaseFiles;



    @OneToMany(mappedBy = "formation",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnore
    private Set<PostComments> postComments;


    @OneToMany(mappedBy = "formation")
    @JsonIgnore
    private Set<Certificat> certificat;






}
