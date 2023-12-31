package com.team2.animalshelter.entity;

import com.team2.animalshelter.entity.enums.AnimalType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "adaptations")
@ToString(exclude = "adaptations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    Integer age;

    String breed;

    Boolean healthy;

    @Enumerated(EnumType.STRING)
    AnimalType animalType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelter_id")
    Shelter shelter;

    @OneToMany(mappedBy = "animal")
    List<Adaptation> adaptations;

}
