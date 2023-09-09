package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.Adaptation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

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

    @Schema(name = "Фото животного")
    MultipartFile photo;

}
