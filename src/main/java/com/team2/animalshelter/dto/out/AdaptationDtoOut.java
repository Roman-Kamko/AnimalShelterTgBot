package com.team2.animalshelter.dto.out;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.LocalDate;

@Value
@Schema(name = "Adaptation", description = "Адаптационный период, устанавливается при взятии животного под опеку")
public class AdaptationDtoOut {

    Long id;

    @Schema(
            description = "Дата начала адаптационного периода",
            example = "2023-09-10"
    )
    LocalDate startDate;

    @Schema(
            description = "Дата окончания адаптационного периода",
            example = "2023-10-10"
    )
    LocalDate endDate;

    @Schema(
            description = "Комментарий волонтера при необходимости, по дефолту устанавливается описание статуса",
            example = "В процессе"
    )
    String comment;

    @Schema(
            name = "Текущий статус адаптации",
            description = "IN_PROGRESS - в процессе, SUCCESSFUL - успешно завершена, " +
                    "NOT_SUCCESSFUL - провалена, EXTENDED - продлен.",
            example = "IN_PROGRESS"
    )
    AdaptationStatus adaptationStatus;

    AnimalDtoOut animal;

    OwnerDto owner;

}
