package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AdaptationStatus;
import lombok.Value;

@Value
public class AdaptationDtoIn {

    String comment;

    Boolean needComment;

    AdaptationStatus adaptationStatus;

    Long animalId;

    Long ownerId;

}
