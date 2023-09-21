package com.team2.animalshelter.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.LocalDate;

@Value
@Schema(name = "Report", description = "Ежедневный отчет опекуна")
public class ReportDtoOut {

    Long id;

    @Schema(description = "Текст отчета", example = "Кормим сухим кормом, аппетит хороший, спокоен, но к обстановке еще не привык")
    String reportMessage;

    @Schema(description = "URL фотографии", example = "http://localhost:8081/api/v1/reports/1/photo")
    String photoUrl;

    @Schema(description = "Дата создания отчета", example = "2023-09-20")
    LocalDate date;

    AdaptationDtoOut adaptation;

}
