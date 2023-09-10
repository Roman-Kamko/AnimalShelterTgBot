package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.AnimalDtoIn;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import com.team2.animalshelter.service.AnimalService;
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
@RequestMapping("/api/v1/animal")
@RequiredArgsConstructor
@Tag(name = "Животные", description = "Эндпоинты для работы с животными")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/find/{id}")
    @Operation(
            summary = "Найти животное по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AnimalDtoOut.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Животное не найдено по идентификатору"
                    )
            }
    )
    public ResponseEntity<AnimalDtoOut> findById(
            @PathVariable @Parameter(description = "Идентификатор животного") Long id
    ) {
        return animalService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    @GetMapping("/adopted/{id}")
    @Operation(
            summary = "Находится ли животное на адаптации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AnimalDtoOut.class))
                            )
                    )
            }
    )
    public Boolean isAdopted(@PathVariable @Parameter(description = "Идентификатор животного") Long id) {
        return animalService.isAdopted(id);
    }

    @GetMapping("/no-all-adopted/{animalType}")
    @Operation(
            summary = "Найти всех животных, которых еще не взяли под опеку",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AnimalDtoOut.class))
                            )
                    )
            }
    )
    public List<AnimalDtoOut> findAllWithoutOwner(
            @PathVariable @Parameter(description = "Тип животного") AnimalType animalType
    ) {
        return animalService.findAllWithoutOwner(animalType);
    }

    @GetMapping("/all-adopted/{animalType}")
    @Operation(
            summary = "Найти всех животных, которых взяли под опеку",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AnimalDtoOut.class))
                            )
                    )
            }
    )
    public List<AnimalDtoOut> findAllAdopted(
            @PathVariable @Parameter(description = "Тип животного") AnimalType animalType
    ) {
        return animalService.findAllAdopted(animalType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать новое животное",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AnimalDtoOut.class))
                            )
                    )
            }
    )
    public AnimalDtoOut create(@RequestBody @Validated AnimalDtoIn animalDtoIn) {
        return animalService.create(animalDtoIn);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить данные животного",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Животное успешно обновлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AnimalDtoOut.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Животное не найдено по идентификатору"
                    )
            }
    )
    public ResponseEntity<AnimalDtoOut> update(
            @PathVariable @Parameter(description = "Идентификатор животного") Long id,
            @RequestBody @Validated AnimalDtoIn animalDtoIn
    ) {
        return animalService.update(id, animalDtoIn)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить животное",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Животное успешно удалено"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Животное  не найдено по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор животного") Long id
    ) {
        return animalService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
