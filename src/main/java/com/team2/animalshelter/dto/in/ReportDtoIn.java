package com.team2.animalshelter.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
@Schema(name = "Report", description = "Ежедневный отчет опекуна")
public class ReportDtoIn {

    @NotBlank
    @Schema(description = "Текст отчета", example = "Кормим сухим кормом, аппетит хороший, спокоен, но к обстановке еще не привык")
    String reportMessage;

    @NotNull
    @Positive
    @Schema(description = "Идентификатор адаптационного периода", example = "1")
    Long adaptationId;

}
