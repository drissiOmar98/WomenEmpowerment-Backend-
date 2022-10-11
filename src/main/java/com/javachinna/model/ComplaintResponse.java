package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplaintResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idResp;
    private String response;
   // @Enumerated(EnumType.STRING)
   // private StatusComplaint status;
    @Temporal(TemporalType.DATE)
    private Date dateResp;
    @ManyToOne
    @JsonIgnore
    private Complaint complaints;
    @ManyToOne
    @JsonIgnore
    private User users;


}
