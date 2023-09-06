package com.team2.animalshelter.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
public class ShelterDto {

    String name;

    String address;

    String phoneNumber;

    String timeTable;

}
