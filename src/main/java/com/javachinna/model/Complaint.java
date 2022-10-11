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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Complaint implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCom;
    @Enumerated(EnumType.STRING)
    private TypeComplaint type;

    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateCom;

    @ManyToOne
    @JsonIgnore
    private User users;

    @OneToMany(mappedBy ="complaints",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ComplaintResponse> complaintResponseSet;



}
