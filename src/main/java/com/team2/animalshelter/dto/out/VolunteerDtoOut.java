package com.team2.animalshelter.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(name = "Volunteer", description = "Волонтер, консультирует пользователей/опекунов")
public class VolunteerDtoOut {

    @Schema(description = "Идентификатор волонтера", example = "32132121")
    Long telegramId;

    @Schema(description = "Псевдоним волонтера", example = "Big-Love")
    String username;

    @Schema(description = "Имя волонтера", example = "Петр")
    String firstname;

    @Schema(description = "Фамилия волонтера", example = "Петров")
    String lastname;

}
