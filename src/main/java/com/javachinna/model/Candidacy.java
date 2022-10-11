package com.javachinna.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table( name = "Candidacy")
public class Candidacy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idCandidacy;
    private String Status ;
    @Temporal(TemporalType.DATE)
    private Date dateOfCandidacy;

    private String cv;

    @ManyToOne
    @JsonIgnore
    private User usersW;

    @ManyToOne
    @JsonIgnore
    private Offres offers;


    @OneToMany(mappedBy = "candidacy" ,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<QuizCandidacy> quizCandidacies;


}
