package com.team2.animalshelter.dto.in;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Schema(name = "Приют")
public class ShelterDtoIn {

    @NotBlank
    @Schema(name = "Название", example = "КотоПес")
    String name;

    @NotBlank
    @Schema(name = "Адрес", example = "г. Астана, ул. Тихая 11, стр. 1")
    String address;

    @NotNull
    @Pattern(regexp = "7[0-9]{10}$")
    @Schema(name = "Контактный телефон", example = "77776665544")
    String phoneNumber;

    @NotBlank
    @Schema(name = "Время работы", example = "Пн-Пт 9:00-18:00")
    String timeTable;

    @Schema(name = "Схема проезда", description = "Картинка")
    MultipartFile image;

}
