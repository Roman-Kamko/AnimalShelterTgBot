package com.team2.animalshelter.dto.out;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
@Schema(name = "Shelter", description = "Приют для кошечек и собачек")
public class ShelterDtoOut {

    @Schema(description = "Название приюта", example = "КотоПес")
    String name;

    @Schema(description = "Адрес приюта", example = "г. Астана, ул. Тихая 11, стр. 1")
    String address;

    @Schema(description = "Контактный телефон приюта", example = "+77776665544")
    String phoneNumber;

    @Schema(description = "Время работы приюта", example = "Пн-Пт 9:00-18:00")
    String timeTable;

    @Hidden
    String drivingDirections;

    @Schema(description = "URL схемы проезда к приюту", example = "http://localhost:8081/api/v1/shelters/map")
    String drivingDirectionsUrl;

}
