package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.OwnerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OwnerServiceTest extends IntegrationTestBase {

    @Autowired
    private OwnerService ownerService;

    private static final long ID = 111111L;

    @Test
    void shouldReturnOwnerWhenFindById() {
        var actualResult = ownerService.findById(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new OwnerDto(
                111111L,
                "Ivan_Ivanov",
                "Иван",
                "Иванов",
                "+79115648532"
        );
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

    @Test
    void shouldReturnAllOwnersWhenFindAll() {
        var owners = ownerService.findAll();
        assertThat(owners).isNotEmpty().hasSize(4);
    }

    @Test
    void shouldCreateOwnerFromUserWhenCreate() {
        var actualResult = ownerService.create(222222L);
        var expected = new OwnerDto(
                222222L,
                "Petr_Petrov",
                "Петр",
                "Петров",
                "+79256279136"
        );
        assertThat(actualResult).isEqualTo(expected);

        var owners = ownerService.findAll();
        assertThat(owners).contains(expected);
    }

    @Test
    void shouldUpdateOwnerWhenUpdate() {
        var requestBody = new OwnerDto(
                111111L,
                "Ivan_Ivanov123",
                "Иван123",
                "Иванов123",
                "+79115648599"
        );
        var actualResult = ownerService.update(ID, requestBody);
        assertThat(actualResult.isPresent()).isTrue();

        var owners = ownerService.findAll();
        actualResult.ifPresent(actual -> {
            assertAll(
                    () -> assertThat(actual).isEqualTo(requestBody),
                    () -> assertThat(owners).contains(actual)
            );
        });
    }

    @Test
    void shouldReturnTrueWhenDeleteIfOwnerIsPresent() {
        long ownerId = 333333L;
        var delete = ownerService.delete(ownerId);
        assertThat(delete).isTrue();

        var owner = ownerService.findById(ownerId);
        assertThat(owner.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeleteIfOwnerIsNotPresent() {
        var delete = ownerService.delete(0L);
        assertThat(delete).isFalse();
    }

}