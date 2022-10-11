package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentUniversity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment ;

    private String comment;

    private Date DateComment;



    private int nbrSignal;





    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private PartnerInstitution partnerInstitution;


    @OneToMany(mappedBy = "commentUniversity",cascade={CascadeType.PERSIST, CascadeType.REMOVE},
            fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<React> reacts;


}
