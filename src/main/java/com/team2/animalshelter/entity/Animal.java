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

}
