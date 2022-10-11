package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Publicity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPublicity;
    private String pub;
    private String canal;
    private Date dateStart;
    private Date dateEnd;
    private Integer initVuNbr;
    private Integer finVuNbr;
    private Integer price;
    private String pubType;


    @ManyToOne
    @JsonIgnore
    private User user;


}
