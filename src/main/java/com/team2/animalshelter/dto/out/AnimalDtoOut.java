package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.entity.enums.AnimalType;
import lombok.Value;

@Value
public class AnimalDtoOut {

    Long id;

    String name;

    Integer age;

    String breed;

    Boolean healthy;

    AnimalType animalType;

}
