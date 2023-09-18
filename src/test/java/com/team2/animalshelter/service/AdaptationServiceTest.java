package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.exception.AnimalAlreadyAdoptedException;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class AdaptationServiceTest extends IntegrationTestBase {

    @Autowired
    private AdaptationService adaptationService;
    private static final long ID = 1L;
    private static OwnerDto owner;
    private static AnimalDtoOut animal;

    @BeforeAll
    static void beforeAll() {
        animal = new AnimalDtoOut(
                3L,
                "Барсик",
                5,
                "Сибирская",
                true,
                AnimalType.CAT
        );
        owner = new OwnerDto(
                111111L,
                "Ivan_Ivanov",
                "Иван",
                "Иванов",
                "+79115648532"
        );
    }

    @Test
    void shouldReturnAdaptationWhenFindById() {
        var actualResult = adaptationService.findById(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new AdaptationDtoOut(
                1L,
                LocalDate.of(2023, 9, 9),
                LocalDate.of(2023, 10, 9),
                null,
                false,
                AdaptationStatus.IN_PROGRESS,
                animal,
                owner
        );
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

    @Test
    void shouldReturnAllAdaptationWhenFindAll() {
        var adaptations = adaptationService.findAll();
        assertThat(adaptations).isNotEmpty().hasSize(3);
    }

    @Test
    void shouldReturnAllAdaptationWithSameStatuesWhenFindAllByAdaptationStatus() {
        var adaptations = adaptationService.findAllByAdaptationStatus(AdaptationStatus.IN_PROGRESS);
        assertThat(adaptations).hasSize(3);
        for (AdaptationDtoOut adaptation : adaptations) {
            assertThat(adaptation.getAdaptationStatus()).isEqualTo(AdaptationStatus.IN_PROGRESS);
        }
    }

    @Test
    void shouldReturnAllProblemAdaptationsWhenFindAllWithProblem() {
        var adaptations = adaptationService.findAllWithProblem();
        assertThat(adaptations).hasSize(1);
        for (AdaptationDtoOut adaptation : adaptations) {
            assertThat(adaptation.getProblem()).isTrue();
        }
    }

    @Test
    void shouldCreateAdaptationWhenCreate() {
        var requestBody = new AdaptationDtoIn(
                AdaptationStatus.IN_PROGRESS,
                1L,
                111111L
        );
        var actual = adaptationService.create(requestBody);
        var expected = new AdaptationDtoOut(
                4L,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                AdaptationStatus.IN_PROGRESS.getDescription(),
                null,
                AdaptationStatus.IN_PROGRESS,
                new AnimalDtoOut(
                        1L,
                        "Пушок",
                        3,
                        "Сфинкс",
                        true,
                        AnimalType.CAT
                ),
                owner
        );
        var adaptations = adaptationService.findAll();
        assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(adaptations).contains(expected)
        );
    }

    @Test
    void shouldThrowAnimalAlreadyAdoptedExceptionIfAnimalAlreadyAdopted() {
        var requestBody = new AdaptationDtoIn(
                AdaptationStatus.IN_PROGRESS,
                3L,
                111111L
        );
        assertThatExceptionOfType(AnimalAlreadyAdoptedException.class)
                .isThrownBy(() -> adaptationService.create(requestBody));
    }

    @Test
    void shouldThrowAnimalNotFoundExceptionIfAnimalIsNotExist() {
        var requestBody = new AdaptationDtoIn(
                AdaptationStatus.IN_PROGRESS,
                0L,
                111111L
        );
        assertThatExceptionOfType(AnimalNotFoundException.class)
                .isThrownBy(() -> adaptationService.create(requestBody));
    }

    @Test
    void shouldUpdateAdaptationWhenUpdate() {
        var requestBody = new AdaptationDtoIn(
                AdaptationStatus.SUCCESSFUL,
                3L,
                111111L
        );
        var actualResult = adaptationService.update(ID, requestBody);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new AdaptationDtoOut(
                1L,
                LocalDate.of(2023, 9, 9),
                LocalDate.of(2023, 10, 9),
                AdaptationStatus.SUCCESSFUL.getDescription(),
                false,
                AdaptationStatus.SUCCESSFUL,
                animal,
                owner
        );
        var adaptations = adaptationService.findAll();
        actualResult.ifPresent(actual -> {
            assertAll(
                    () -> assertThat(actual).isEqualTo(expected),
                    () -> assertThat(adaptations).contains(actual)
            );
        });
    }

    @Test
    void shouldReturnTrueWhenDeleteIfAdaptationExist() {
        var delete = adaptationService.delete(ID);
        assertThat(delete).isTrue();

        var adaptation = adaptationService.findById(ID);
        assertThat(adaptation.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeleteIfAdaptationNotExist() {
        var delete = adaptationService.delete(0L);
        assertThat(delete).isFalse();
    }

}