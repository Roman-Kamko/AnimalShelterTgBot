package com.team2.animalshelter.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
public class UserDto {

    Long telegramId;

    String username;

    String firstname;

    String lastname;

}
