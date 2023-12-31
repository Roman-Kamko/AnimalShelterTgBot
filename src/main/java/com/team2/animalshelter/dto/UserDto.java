package com.team2.animalshelter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Schema(name = "User", description = "Пользователь ТГ ботом, потенциальный опекун")
public class UserDto {

    @NotNull
    @Schema(description = "Телеграм id пользователя", example = "12312321")
    Long telegramId;

    @NotBlank
    @Schema(description = "Псевдоним пользователя", example = "Jon_Snow")
    String username;

    @NotBlank
    @Schema(description = "Имя пользователя", example = "Иван")
    String firstname;

    @NotBlank
    @Schema(description = "Фамилия пользователя", example = "Иванов")
    String lastname;

    @Schema(description = "Контактный телефон пользователя", example = "79998887766")
    @Pattern(regexp = "\\+7[0-9]{10}$")
    String phoneNumber;

}
