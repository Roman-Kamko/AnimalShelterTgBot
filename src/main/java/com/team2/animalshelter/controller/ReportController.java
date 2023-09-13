package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.ReportDtoIn;
import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.exception.ReportNotFoundException;
import com.team2.animalshelter.exception.ShelterNotFoundException;
import com.team2.animalshelter.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Отчет", description = "Эндпоинты для работы с отчетами")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(
            summary = "Получить список всех отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ReportDtoOut.class))
                            )
                    )
            }
    )
    public List<ReportDtoOut> findAll() {
        return reportService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить отчет по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют успешно найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDtoOut.class)
                            )
                    )
            }
    )
    public ResponseEntity<ReportDtoOut> findById(
            @PathVariable @Parameter(description = "Идентификатор отчета") Long id
    ) {
        return reportService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

    @Operation(
            summary = "Получить фото отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден по идентификатору или у отчета нет фото"
                    )
            }
    )
    @GetMapping(value = "/{id}/photo")
    public ResponseEntity<byte[]> findDrivingDirections(@PathVariable Long id) {
        return reportService.getImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .contentLength(content.length)
                        .body(content)
                )
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать новый отчет",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Отчет успешно создан"
                    )
            }
    )
    public ReportDtoOut create(
            @RequestPart(value = "dto") @Validated ReportDtoIn reportDtoIn,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) {
        return reportService.create(reportDtoIn, image);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Обновить данные отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет успешно обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ReportDtoOut.class)
                            )
                    )
            }
    )
    public ResponseEntity<ReportDtoOut> update(
            @PathVariable @Parameter(description = "Идентификатор отчета") Long id,
            @RequestPart(value = "dto") @Validated ReportDtoIn reportDtoIn,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) {
        return reportService.update(id, reportDtoIn, image)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ShelterNotFoundException(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить отчет",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Отчет успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Отчет не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор отчета") Long id
    ) {
        return reportService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
