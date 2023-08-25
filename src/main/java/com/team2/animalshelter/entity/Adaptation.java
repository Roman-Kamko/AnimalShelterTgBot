package com.team2.animalshelter.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "adaptation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Adaptation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate endsDate;

    String comment;

    Boolean isNeedComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal", nullable = false)
    Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    User user;
}
