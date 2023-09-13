package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.exception.UserNotFoundException;
import com.team2.animalshelter.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Пользователь", description = "Эндпоинты для работы с пользователями")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(
            summary = "Получить список всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос выполнен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )
                    )
            }
    )
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<UserDto> findById(
            @PathVariable @Parameter(description = "Идентификатор пользователя") Long id
    ) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создать нового пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно создан",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    public UserDto create(@RequestBody @Validated UserDto userDto) {
        return userService.create(userDto);
    }


    @PutMapping
    @Operation(
            summary = "Обновить данные пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<UserDto> update(
            @RequestBody @Validated UserDto userDto
    ) {
        return userService.update(userDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(userDto.getTelegramId()));
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Удалить пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Пользователь успешно удален"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден по идентификатору"
                    )
            }
    )
    public ResponseEntity<?> delete(
            @PathVariable @Parameter(description = "Идентификатор пользователя") Long id
    ) {
        return userService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
