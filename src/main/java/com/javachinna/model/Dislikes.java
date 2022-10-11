package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dislikes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer nbrDislikes;
    private Integer nbrSubsDislikes;

    @Temporal(TemporalType.DATE)
    private Date createAt;

    @ManyToOne
    @JsonIgnore
    private PostComments postComments;

    @ManyToOne
    @JsonIgnore
    private Subscription subscss;

    /*
    @ManyToOne
    @JsonIgnore
    private User user;


     */


}
