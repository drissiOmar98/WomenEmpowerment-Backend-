package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingPartner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer idRp;

    @Enumerated(EnumType.STRING)
    private TypeRating typeRating;


    @JsonIgnore
    @ManyToOne
    private PartnerInstitution partnerInstitution;

    @JsonIgnore
    @ManyToOne
    private User user;


}
