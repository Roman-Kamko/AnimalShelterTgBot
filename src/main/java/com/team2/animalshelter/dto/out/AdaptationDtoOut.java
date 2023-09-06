package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.entity.enums.AdaptationStatus;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
@Value
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
