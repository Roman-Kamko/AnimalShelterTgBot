package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.exception.OwnerNotFoundException;
import com.team2.animalshelter.service.OwnerService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
@Tag(name = "Опекун", description = "Эндпоинты для работы с опекунами")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Найти опекуна по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<OwnerDto> findById(
            @PathVariable @Parameter(description = "Идентификатор опекуна") Long id
    ) {
        return ownerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    @GetMapping
    @Operation(
            summary = "Получить список всех опекунов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = OwnerDto.class))
                            )
                    )
            }
    )
    public List<OwnerDto> findAll() {
        return ownerService.findAll();
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать нового опекуна на основе пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDto.class)
                            )
                    )
            }
    )
    public OwnerDto create(
            @PathVariable
            @Parameter(description = "Идентификатор пользователя на основе которого необходимо создать опекуна")
            Long id
    ) {
        return ownerService.create(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные опекуна",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Опекун успешно обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = OwnerDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<OwnerDto> update(
            @PathVariable @Parameter(description = "Идентификатор опекуна") Long id,
            @RequestBody @Validated OwnerDto ownerDto
    ) {
        return ownerService.update(id, ownerDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OwnerNotFoundException(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить опекуна",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Опекун успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Опекун не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор опекуна") Long id
    ) {
        return ownerService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
