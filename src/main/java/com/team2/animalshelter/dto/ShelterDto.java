package com.team2.animalshelter.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShelterDto {

    String name;

    String address;

    String phoneNumber;

    String timeTable;
}
