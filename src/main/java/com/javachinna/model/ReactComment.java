package com.javachinna.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity

public class ReactComment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer idReact;

    @Enumerated(EnumType.STRING)
    private TypeRating typeRating;


    @JsonIgnore
    @ManyToOne
    private Comment comment;

    @JsonIgnore
    @ManyToOne
    private User user;

}
