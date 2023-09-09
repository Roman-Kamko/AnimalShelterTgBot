package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.dto.out.VolunteerDtoOut;
import com.team2.animalshelter.exception.VolunteerNotFoundException;
import com.team2.animalshelter.service.VolunteerService;
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
@RequestMapping("/api/v1/volunteer")
@RequiredArgsConstructor
@Tag(name = "Волонтеры", description = "Эндпоинты для работы с волотерами")
public class VolunteerController {

    private final VolunteerService volunteerService;

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Найти волонтера по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = VolunteerDtoOut.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтер не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<VolunteerDtoOut> findById(
            @PathVariable @Parameter(description = "Идентификатор волонтера") Long id
    ) {
        return volunteerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new VolunteerNotFoundException(id));
    }

    @GetMapping("/find-all")
    @Operation(
            summary = "Получить список всех волонтеров",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = VolunteerDtoOut.class))
                            )
                    )
            }
    )
    public List<VolunteerDtoOut> findAll() {
        return volunteerService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать нового волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = VolunteerDtoOut.class))
                            )
                    )
            }
    )
    public VolunteerDtoOut create(@RequestBody @Validated VolunteerDtoIn volunteerDtoIn) {
        return volunteerService.create(volunteerDtoIn);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Волонтер успешно обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = VolunteerDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтер не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<VolunteerDtoOut> update(
            @PathVariable @Parameter(description = "Идентификатор волонтера") Long id,
            @RequestBody @Validated VolunteerDtoIn volunteerDtoIn
    ) {
        return volunteerService.update(id, volunteerDtoIn)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new VolunteerNotFoundException(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить волонтера",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Волонтер успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Волонтер не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор волонтера") Long id
    ) {
        return volunteerService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
