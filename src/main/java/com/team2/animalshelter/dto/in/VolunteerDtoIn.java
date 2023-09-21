package com.team2.animalshelter.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Schema(name = "Volunteer", description = "Волонтер, консультирует пользователей/опекунов")
public class VolunteerDtoIn {

    @NotNull
    @Schema(description = "Идентификатор волонтера", example = "32132121")
    Long telegramId;

    @NotBlank
    @Schema(description = "Псевдоним волонтера", example = "Big-Love")
    String username;

    @NotBlank
    @Schema(description = "Имя волонтера", example = "Петр")
    String firstname;

    @NotBlank
    @Schema(description = "Фамилия волонтера", example = "Петров")
    String lastname;

}
