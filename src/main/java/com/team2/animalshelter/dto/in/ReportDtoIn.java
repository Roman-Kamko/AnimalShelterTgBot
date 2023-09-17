package com.team2.animalshelter.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
@Schema(name = "Отчет")
public class ReportDtoIn {

    @NotBlank
    @Schema(name = "Текст отчета", example = "Кормим сухим кормом, аппетит хороший, спокоен, но к обстановке еще не привык")
    String reportMessage;

    @NotNull
    @Positive
    @Schema(name = "Идентификатор адаптационного периода", example = "1")
    Long adaptationId;

}
