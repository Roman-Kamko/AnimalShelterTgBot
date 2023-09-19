package com.team2.animalshelter.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Schema(name = "Волонтер")
public class VolunteerDtoIn {

    @NotNull
    @Schema(name = "Идентификатор волонтера", example = "32132121")
    Long telegramId;

    @NotBlank
    @Schema(name = "Псевдоним волонтера", example = "Big-Love")
    String username;

    @NotBlank
    @Schema(name = "Имя волонтера", example = "Петр")
    String firstname;

    @NotBlank
    @Schema(name = "Фамилия волонтера", example = "Петров")
    String lastname;

}
