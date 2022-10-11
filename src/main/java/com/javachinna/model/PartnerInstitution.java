package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerInstitution implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idPartner;
    private String Name;
    private String Country;
    @Enumerated(EnumType.STRING)
    private GeographicalArea geographicalArea;
    private String Language;
    private String email;


    private String picture;
    //private String video;
    private  int CapacityReception;
    private boolean active;
    private double fees ;
    private String description;
    @Enumerated(EnumType.STRING)
    private specialty special ;


    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
            fetch=FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy ="partnerInstitution" ,cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
            fetch=FetchType.LAZY)
    @JsonIgnore
    private Set<CandidacyUniversity>candidacies;

    @OneToMany(mappedBy = "partnerInstitution",cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
            fetch=FetchType.LAZY)
    @JsonIgnore
    private Set<CommentUniversity> commentUniversities;


    @OneToMany(mappedBy = "partnerInstitution",cascade={CascadeType.PERSIST, CascadeType.REMOVE},
            fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<Rating>ratings;

    @OneToMany(mappedBy = "partnerInstitution",cascade={CascadeType.PERSIST, CascadeType.REMOVE},
            fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<React> reacts;

    @OneToMany(mappedBy = "partnerInstitution",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DatabaseFile> fileUploads;






}
