package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AnimalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.*;

@Value
@Schema(name = "Животное")
public class AnimalDtoIn {

    @NotBlank
    @Schema(name = "Кличка", example = "Пушок")
    String name;

    @NotNull
    @Positive
    @Schema(name = "Возраст", example = "3")
    Integer age;

    @NotBlank
    @Schema(name = "Порода", example = "Мейн-кун")
    String breed;

    @NotNull

    @Pattern(regexp = "true$|false$", message = "разрешенный ввод: true или false")
    @Schema(
            name = "Здоровье",
            description = "true - здоров, false - имеются проблемы со здоровьем",
            example = "true"
    )
    String healthy;

    @NotNull
    //@Pattern(regexp = "CAT$|DOG$")
    @Schema(name = "Тип животного", example = "CAT")
    AnimalType animalType;

}
