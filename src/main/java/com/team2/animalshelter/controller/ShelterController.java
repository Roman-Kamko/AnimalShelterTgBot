package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.exception.ShelterNotFoundException;
import com.team2.animalshelter.service.ShelterService;
import io.swagger.v3.oas.annotations.Hidden;
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
@RequestMapping("/api/v1/shelters")
@RequiredArgsConstructor
@Tag(name = "Приют", description = "Эндпоинты для работы с приютами")
public class ShelterController {

    private final ShelterService shelterService;

    @GetMapping
    @Operation(
            summary = "Получить список всех приютов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = ShelterDtoOut.class))
                            )
                    )
            }
    )
    public List<ShelterDtoOut> findAll() {
        return shelterService.findAll();
    }

    @Hidden
    @GetMapping("/{id}")
    @Operation(
            summary = "Получить приют по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют успешно найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterDtoOut.class)
                            )
                    )
            }
    )
    public ResponseEntity<ShelterDtoOut> findById(
            @PathVariable @Parameter(description = "Идентификатор приюта") Long id
    ) {
        return shelterService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ShelterNotFoundException(id));
    }

    @Operation(
            summary = "Получить схему проезда",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Приют не найден по идентификатору или у приюта нет схемы проезда"
                    )
            }
    )
    @GetMapping(value = "/{id}/map")
    public ResponseEntity<byte[]> findDrivingDirections(@PathVariable Long id) {
        return shelterService.getImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .contentLength(content.length)
                        .body(content)
                )
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @Hidden
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать новый приют",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Приют успешно создан"
                    )
            }
    )
    public ShelterDtoOut create(
            @RequestPart(value = "dto") @Validated ShelterDtoIn shelterDtoIn,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) {
        return shelterService.create(shelterDtoIn, image);
    }


    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Обновить данные приюта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Приют успешно обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterDtoOut.class)
                            )
                    )
            }
    )
    public ResponseEntity<ShelterDtoOut> update(
            @PathVariable @Parameter(description = "Идентификатор приюта") Long id,
            @RequestPart(value = "dto") @Validated ShelterDtoIn shelterDtoIn,
            @RequestPart(value = "file", required = false) MultipartFile image
    ) {
        return shelterService.update(id, shelterDtoIn, image)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ShelterNotFoundException(id));
    }

    @Hidden
    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить приют",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Приют успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Приют не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор приюта") Long id
    ) {
        return shelterService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
