package com.team2.animalshelter.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long telegramId;

    String username;

    String firstname;

    String lastname;
}
