package com.team2.animalshelter.controller;

import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.exception.UserNotFoundException;
import com.team2.animalshelter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
                    @ApiResponse(responseCode = "200", description = "Запрос выполнен")
            }
    )
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить пользователя по идентификатору",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Запрос выполнен"),
                    @ApiResponse(responseCode = "404", description = "Сервер не может найти данные согласно запросу.")
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
                    @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Ошибка в параметрах запроса.")
            }
    )
    public UserDto create(@RequestBody @Validated UserDto userDto) {
        return userService.create(userDto);
    }

}
