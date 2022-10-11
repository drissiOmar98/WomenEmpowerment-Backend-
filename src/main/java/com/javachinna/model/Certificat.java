package com.javachinna.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Certificat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Temporal(TemporalType.TIMESTAMP)
    Date date ;

    String path;

    @ManyToOne
    User user;

    @ManyToOne
    Formation formation;

    public Certificat(String fileName, String contentType, byte[] bytes) {
    }


}
