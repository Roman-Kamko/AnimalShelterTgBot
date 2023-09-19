package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.exception.ShelterNotFoundException;
import com.team2.animalshelter.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/shelters")
@RequiredArgsConstructor
@Tag(name = "Приют", description = "Эндпоинты для работы с приютами")
public class ShelterController {

    private final ShelterService shelterService;

    @GetMapping
    @Operation(
            summary = "Получить приют",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ShelterDtoOut.class)
                            )
                    )
            }
    )
    public ShelterDtoOut getShelter() {
        return shelterService.getShelter()
                .orElseThrow(ShelterNotFoundException::new);
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
    @GetMapping(value = "/map")
    public ResponseEntity<byte[]> findDrivingDirections() {
        return shelterService.getImage()
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .contentLength(content.length)
                        .body(content)
                )
                .orElseGet(ResponseEntity.notFound()::build);
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
        return shelterService.update(shelterDtoIn, image)
                .map(ResponseEntity::ok)
                .orElseThrow(ShelterNotFoundException::new);
    }

}
