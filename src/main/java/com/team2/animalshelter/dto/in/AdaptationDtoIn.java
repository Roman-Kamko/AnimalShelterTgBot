package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.enums.AdaptationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Value
@Schema(name = "Adaptation", description = "Адаптационный период, устанавливается при взятии животного под опеку")
public class AdaptationDtoIn {

    @NotNull
    //@Pattern(regexp = "IN_PROGRESS$|SUCCESSFUL$|NOT_SUCCESSFUL$|EXTENDED$")
    @Schema(
            description = "IN_PROGRESS - в процессе, SUCCESSFUL - успешно завершена, " +
                    "NOT_SUCCESSFUL - провалена, EXTENDED - продлен; по умолчанию - IN_PROGRESS",
            example = "IN_PROGRESS"
    )
    AdaptationStatus adaptationStatus;

    @NotNull
    @Positive
    @Schema(description = "Идентификатор животного", example = "1")
    Long animalId;

    @NotNull
    @Positive
    @Schema(description = "Идентификатор опекуна", example = "12312321")
    Long ownerId;

}
