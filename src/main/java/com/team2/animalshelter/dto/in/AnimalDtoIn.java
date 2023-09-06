package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AnimalType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class AnimalDtoIn {

    String name;

    Integer age;

    String breed;

    Boolean healthy;

    @Enumerated(EnumType.STRING)
    AnimalType animalType;
}
