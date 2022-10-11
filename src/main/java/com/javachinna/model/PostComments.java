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
@EqualsAndHashCode
@Table( name = "PostComments")
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComn")
    private Integer idComn;

    private String message ;
    @Temporal(TemporalType.DATE)
    private Date createAt;


    @ManyToOne
    @JsonIgnore
    private User userC;

    @ManyToOne
    @JsonIgnore
    private Formation formation;


    @OneToMany (mappedBy = "postComments",cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private Set<Likes> likes;

    @OneToMany (mappedBy = "postComments",cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private Set<Dislikes> Dislikes;



}
