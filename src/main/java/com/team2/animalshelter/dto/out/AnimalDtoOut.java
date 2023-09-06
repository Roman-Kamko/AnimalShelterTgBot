package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.entity.enums.AnimalType;
import lombok.Value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Value
public class AnimalDtoOut {

    Long id;

    String name;

    Integer age;

    String breed;

    Boolean healthy;

    @Enumerated(EnumType.STRING)
    AnimalType animalType;

    List<AdaptationDtoOut> adaptationDtoOuts;
    
}
