package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.dto.ShelterDto;
import lombok.Value;

@Value
public class VolunteerDtoOut {

    Long telegramId;

    String username;

    String firstname;

    String lastname;

    ShelterDto shelter;

}
