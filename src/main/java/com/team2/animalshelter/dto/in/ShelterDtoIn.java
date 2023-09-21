package com.team2.animalshelter.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Schema(name = "Shelter", description = "Приют для кошечек и собачек")
public class ShelterDtoIn {

    @NotBlank
    @Schema(description = "Название приюта", example = "КотоПес")
    String name;

    @NotBlank
    @Schema(description = "Адрес приюта", example = "г. Астана, ул. Тихая 11, стр. 1")
    String address;

    @NotNull
    @Pattern(regexp = "\\+7[0-9]{10}$")
    @Schema(description = "Контактный телефон приюта", example = "+77776665544")
    String phoneNumber;

    @NotBlank
    @Schema(description = "Время работы приюта", example = "Пн-Пт 9:00-18:00")
    String timeTable;

}
