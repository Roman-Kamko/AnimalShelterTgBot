package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.in.AnimalDtoIn;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AnimalServiceTest extends IntegrationTestBase {

    @Autowired
    private AnimalService animalService;
    private static final long ID = 1L;

    @Test
    void shouldReturnAnimalWhenFindById() {
        var actualResult = animalService.findById(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new AnimalDtoOut(
                1L,
                "Пушок",
                3,
                "Сфинкс",
                true,
                AnimalType.CAT
        );
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

    @Test
    void shouldReturnAllAdoptedCatWhenFindAllAdopted() {
        var actualResult = animalService.findAllAdopted(AnimalType.CAT);
        assertThat(actualResult).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldReturnAllAdoptedDogWhenFindAllAdopted() {
        var actualResult = animalService.findAllAdopted(AnimalType.DOG);
        assertThat(actualResult).isNotEmpty().hasSize(1);
    }

    @Test
    void shouldReturnAllFreeCatWhenFindAllWithoutOwner() {
        var actualResult = animalService.findAllWithoutOwner(AnimalType.CAT);
        assertThat(actualResult).isNotEmpty().hasSize(3);
    }

    @Test
    void shouldReturnAllFreeDogWhenFindAllWithoutOwner() {
        var actualResult = animalService.findAllWithoutOwner(AnimalType.DOG);
        assertThat(actualResult).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldReturnTrueIfAnimalAdopted() {
        var adopted = animalService.isAdopted(2L);
        assertThat(adopted).isTrue();
    }

    @Test
    void shouldReturnFalseIfAnimalNotAdopted() {
        var adopted = animalService.isAdopted(ID);
        assertThat(adopted).isFalse();
    }

    @Test
    void shouldThrowExceptionIfAnimalNotPresent() {
        assertThatExceptionOfType(AnimalNotFoundException.class)
                .isThrownBy(() -> animalService.isAdopted(0L));
    }

    @Test
    void shouldCreateAnimalWhenCreate() {
        var requestBody = new AnimalDtoIn(
                "Пушок",
                3,
                "Сфинкс",
                "true",
                AnimalType.CAT
        );
        var expected = new AnimalDtoOut(
                9L,
                "Пушок",
                3,
                "Сфинкс",
                true,
                AnimalType.CAT
        );

        var actualResult = animalService.create(requestBody);
        assertThat(actualResult).isEqualTo(expected);

        var cats = animalService.findAllWithoutOwner(AnimalType.CAT);
        assertThat(cats).contains(expected);
    }

    @Test
    void shouldUpdateAnimalWhenUpdate() {
        var requestBody = new AnimalDtoIn(
                "Плюх",
                4,
                "Сфинкс",
                "true",
                AnimalType.CAT
        );
        var actualResult = animalService.update(ID, requestBody);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new AnimalDtoOut(
                1L,
                "Плюх",
                4,
                "Сфинкс",
                true,
                AnimalType.CAT
        );
        assertThat(actualResult).isEqualTo(Optional.of(expected));

        var cats = animalService.findAllWithoutOwner(AnimalType.CAT);
        assertThat(cats).contains(expected);
    }

    @Test
    void shouldReturnTrueWhenDeleteIfAnimalIsPresent() {
        var delete = animalService.delete(ID);
        assertThat(delete).isTrue();

        var animal = animalService.findById(ID);
        assertThat(animal.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeleteIfAnimalIsNotPresent() {
        var delete = animalService.delete(0L);
        assertThat(delete).isFalse();
    }

}