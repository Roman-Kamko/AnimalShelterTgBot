package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.testcontainers.shaded.com.google.common.net.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ShelterServiceTest extends IntegrationTestBase {

    @Autowired
    private ShelterService shelterService;

    @Test
    void shouldReturnShelterWhenGetShelter() {
        var expected = new ShelterDtoOut(
                "КотоПес",
                "г. Астана, ул. Лесная, д. 3.",
                "Телефон охраны для оформления пропуска: +7-888-88-88; Общий телефон: +7-999-99-99",
                "Часы работы: Пн-Пт 08:00 - 20:00",
                "address.jpg",
                "http://localhost:8081/api/v1/shelters/map"
        );
        var actualResult = shelterService.getShelter();
        assertThat(actualResult.isPresent()).isTrue();
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

    @Test
    void shouldUpdateShelterWhenUpdate() throws IOException {
        var partOne = new ShelterDtoIn(
                "КотоПес123",
                "г. Астана, ул. Лесная, д. 3А.",
                "Телефон охраны для оформления пропуска: +7-777-77-77; Общий телефон: +7-999-99-99",
                "Часы работы: Пн-Пт 08:00 - 20:00"
        );
        var partTwo = new MockMultipartFile(
                "image",
                "testAddress.jpg",
                MediaType.ANY_IMAGE_TYPE.type(),
                Files.readAllBytes(Path.of("testAddress.jpg"))
        );
        var actualResult = shelterService.update(partOne, partTwo);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new ShelterDtoOut(
                "КотоПес123",
                "г. Астана, ул. Лесная, д. 3А.",
                "Телефон охраны для оформления пропуска: +7-777-77-77; Общий телефон: +7-999-99-99",
                "Часы работы: Пн-Пт 08:00 - 20:00",
                "testAddress.jpg",
                "http://localhost:8081/api/v1/shelters/map"
        );
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

    @Test
    void shouldReturnImageWhenGetImage() throws IOException {
        var actualResult = shelterService.getImage();
        assertThat(actualResult.isPresent()).isTrue();

        var expected = Files.readAllBytes(Path.of("image", "shelters", "address.jpg"));
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

}