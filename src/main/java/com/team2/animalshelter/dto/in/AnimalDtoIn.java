package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AnimalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.*;

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

    @Pattern(regexp = "true$|false$", message = "разрешенный ввод: true или false")
    @Schema(
            description = "true - здоров, false - имеются проблемы со здоровьем",
            example = "true"
    )
    String healthy;

    @NotNull
    //@Pattern(regexp = "CAT$|DOG$")
    @Schema(description = "Тип животного", example = "CAT")
    AnimalType animalType;

}
