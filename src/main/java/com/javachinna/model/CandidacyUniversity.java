package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidacyUniversity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idCandidacy;
    private Date DateOFCandidacy;
    @Enumerated(EnumType.STRING)
    private  StatusOfCandidacy status;

    private Date dateResponse;


    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
            fetch=FetchType.LAZY)
    @JsonIgnore
    private User user;

    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE,CascadeType.MERGE},
            fetch=FetchType.LAZY)
    @JsonIgnore
    private PartnerInstitution partnerInstitution;

    @ManyToOne
    @JsonIgnore
    private DatabaseFile fileUpload;



}
