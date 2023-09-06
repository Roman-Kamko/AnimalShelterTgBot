package com.team2.animalshelter.dto.in;

import lombok.Value;

@Value
public class VolunteerDtoIn {

    Long telegramId;

    String username;

    String firstname;

    String lastname;

    Long shelterId;

}
