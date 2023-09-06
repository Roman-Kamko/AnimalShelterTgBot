package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AdaptationStatus;
import lombok.Value;

import java.time.LocalDate;
@Value
public class AdaptationDtoIn {

    LocalDate startDate;

    LocalDate endDate;

    String comment;

    Boolean needComment;

    AdaptationStatus adaptationStatus;

    Long animalId;

    Long ownerId;

}
