package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import lombok.Value;

import java.util.List;
@Value
public class OwnerDtoOut {

    Long telegramId;

    String username;

    String firstname;

    String lastname;

}
