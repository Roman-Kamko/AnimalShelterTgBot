package com.team2.animalshelter.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "animal")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    Integer age;

    Boolean isHealthy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", nullable = false)
    User user;

<<<<<<< HEAD
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter", nullable = false)
    Shelter shelter;

=======
>>>>>>> origin/feature-nikitin
    @OneToMany(mappedBy = "animal")
    List<Adaptation> adaptations;
}