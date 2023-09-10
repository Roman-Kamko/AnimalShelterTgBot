package com.team2.animalshelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Schema(description = "Опекун")
public class OwnerDto {

    @NotNull
    @Schema(description = "Телеграм id опекуна", example = "12312321")
    Long telegramId;

    @NotBlank
    @Schema(description = "Псевдоним опекуна", example = "Jon_Snow")
    String username;

    @NotBlank
    @Schema(description = "Имя опекуна", example = "Иван")
    String firstname;

    @NotBlank
    @Schema(description = "Фамилия опекуна", example = "Иванов")
    String lastname;

    @Schema(description = "Контактный телефон опекуна", example = "79998887766")
    @Pattern(regexp = "7[0-9]{10}$")
    String phoneNumber;

}
