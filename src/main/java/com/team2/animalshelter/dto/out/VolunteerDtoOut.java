package com.team2.animalshelter.dto.out;

import lombok.Value;

@Value
public class VolunteerDtoOut {

    Long telegramId;

    String username;

    String firstname;

    String lastname;

    ShelterDtoOut shelter;

}
