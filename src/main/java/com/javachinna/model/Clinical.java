package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Clinical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClinical;
    private String nameClinical;
    private String address;
    private Long telephoneCl;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> doctors;
}
