package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AnimalType;
import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Value
public class AnimalDtoIn {

    String name;

    Integer age;

    String breed;

    Boolean healthy;

    AnimalType animalType;

}
