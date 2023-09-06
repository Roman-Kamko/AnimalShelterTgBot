package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.entity.enums.AdaptationStatus;

import java.time.LocalDate;

public class AdaptationDtoOut {

    Long id;
    LocalDate startDate;
    LocalDate endDate;
    String comment;
    Boolean needComment;
    AdaptationStatus adaptationStatus;
    AnimalDtoOut animalDtoOut;
    OwnerDtoOut ownerDtoOut;

}
