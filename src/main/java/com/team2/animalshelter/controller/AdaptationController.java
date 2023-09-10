package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.exception.AdaptationNotFoundException;
import com.team2.animalshelter.service.AdaptationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/v1/adaptation")
@RequiredArgsConstructor
@Tag(name = "Адаптации", description = "Эндпоинты для работы с адоптациями")
public class AdaptationController {

    private final AdaptationService adaptationService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Найти адоптацию по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdaptationDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Адоптация не найдена по идентификатору"
                    )
            }
    )
    public ResponseEntity<AdaptationDtoOut> findById(
            @PathVariable @Parameter(description = "Идентификатор адоптации") Long id
    ) {
        return adaptationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AdaptationNotFoundException(id));
    }

    @GetMapping
    @Operation(
            summary = "Получить список всех адоптаций",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdaptationDtoOut.class))
                            )
                    )
            }
    )
    public List<AdaptationDtoOut> findAll() {
        return adaptationService.findAll();
    }

    @GetMapping("/{status}")
    @Operation(
            summary = "Найти адоптации по статусу",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdaptationDtoOut.class))
                            )
                    )
            }
    )
    public List<AdaptationDtoOut> findAllByAdaptationStatus(
            @PathVariable
            @Parameter(description = "Статус адоптации")
            @Pattern(regexp = "IN_PROGRESS$|SUCCESSFUL$|NOT_SUCCESSFUL$")
            AdaptationStatus status
    ) {
        return adaptationService.findAllByAdaptationStatus(status);
    }

    @GetMapping("/problem")
    @Operation(
            summary = "Найти проблемные адоптации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdaptationDtoOut.class))
                            )
                    )
            }
    )
    public List<AdaptationDtoOut> findAllWithProblem() {
        return adaptationService.findAllWithProblem();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать новую адаптацию",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdaptationDtoOut.class)
                            )
                    )
            }
    )
    public AdaptationDtoOut create(@RequestBody @Validated AdaptationDtoIn adaptationDtoIn) {
        return adaptationService.create(adaptationDtoIn);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные адаптации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Адаптация успешно обновлена",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdaptationDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Адаптация не найдена по идентификатору"
                    )
            }
    )
    public ResponseEntity<AdaptationDtoOut> update(
            @PathVariable @Parameter(description = "Идентификатор адаптации") Long id,
            @RequestBody @Validated AdaptationDtoIn adaptationDtoIn
    ) {
        return adaptationService.update(id, adaptationDtoIn)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AdaptationNotFoundException(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить адаптацию",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Адаптация успешно удалена"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Адаптация не найдена по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор адаптации") Long id
    ) {
        return adaptationService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
