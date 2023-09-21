package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.entity.enums.AnimalType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(name = "Animal", description = "Животное")
public class AnimalDtoOut {

    Long id;

    @Schema(description = "Кличка", example = "Пушок")
    String name;

    @Schema(description = "Возраст", example = "3")
    Integer age;

    @Schema(description = "Порода", example = "Мейн-кун")
    String breed;

    @Schema(
            description = "true - здоров, false - имеются проблемы со здоровьем",
            example = "true"
    )
    Boolean healthy;

    @Schema(description = "Тип животного", example = "CAT")
    AnimalType animalType;

}
