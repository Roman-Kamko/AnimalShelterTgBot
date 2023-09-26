package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AnimalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
@Schema(name = "Animal", description = "Животное")
public class AnimalDtoIn {

    @NotBlank
    @Schema(description = "Кличка", example = "Пушок")
    String name;

    @NotNull
    @Positive
    @Schema(description = "Возраст", example = "3")
    Integer age;

    @NotBlank
    @Schema(description = "Порода", example = "Мейн-кун")
    String breed;

    @NotNull
    @Schema(
            description = "true - здоров, false - имеются проблемы со здоровьем",
            example = "true"
    )
    Boolean healthy;

    @NotNull
    @Schema(description = "Тип животного", example = "CAT")
    AnimalType animalType;

}
