package com.javachinna.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString

public class Topic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTopic;
    private String title;
    private String content;
    private String category;
    private Date createdDate;
    private String media;
    private Type type;

    @Max(5)
    @Min(0)
    private Double Rating;


    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "topic")
    @JsonIgnore
    private Set<Comment> comments;




}
