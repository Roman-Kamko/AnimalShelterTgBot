package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AdaptationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Value
@Schema(name = "Адаптационный период")
public class AdaptationDtoIn {

    //    @Schema(name = "Комментарий волонтера", example = "Просрочена отчетность")
//    String comment;
//
//    @Schema(
//            name = "Необходимость в комментарии",
//            description = "true - с адаптационным периодом что-то не так, по умолчанию false",
//            example = "false"
//    )
//    @Pattern(regexp = "true$|false$")
//    Boolean needComment;
//
    @NotNull
    @Pattern(regexp = "IN_PROGRESS$|SUCCESSFUL$|NOT_SUCCESSFUL$")
    @Schema(
            name = "Текущий статус адаптации",
            description = "IN_PROGRESS - в процессе, SUCCESSFUL - успешно завершена, NOT_SUCCESSFUL - провалена; по умолчанию - IN_PROGRESS",
            example = "IN_PROGRESS"
    )
    AdaptationStatus adaptationStatus;

    @NotNull
    @Positive
    @Schema(name = "Идентификатор животного", example = "1")
    Long animalId;

    @NotNull
    @Positive
    @Schema(name = "Идентификатор опекуна", example = "12312321")
    Long ownerId;

}
