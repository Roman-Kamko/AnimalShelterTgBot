package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.dto.out.VolunteerDtoOut;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class VolunteerServiceTest extends IntegrationTestBase {

    @Autowired
    private VolunteerService volunteerService;
    private static ShelterDtoOut shelter;
    private static final long ID = 666666L;

    @BeforeAll
    static void beforeAll() {
        shelter = new ShelterDtoOut(
                1L,
                "КотоПес",
                "г. Астана, ул. Лесная, д. 3.",
                "Телефон охраны для оформления пропуска: +7-888-88-88; Общий телефон: +7-999-99-99",
                "Часы работы: Пн-Пт 08:00 - 20:00",
                "address.jpg",
                "http://localhost:8081/api/v1/shelters/3/map"
        );
    }

    @Test
    void shouldReturnOptionalOfVolunteerWhenFindById() {
        var actualResult = volunteerService.findById(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new VolunteerDtoOut(
                666666L,
                "Dimon",
                "Дмитрий",
                "Дмитриев",
                shelter
        );
        actualResult.ifPresent(volunteer -> assertThat(volunteer).isEqualTo(expected));
    }

    @Test
    void shouldReturnAllVolunteersWhenFindAll() {
        var volunteers = volunteerService.findAll();
        assertThat(volunteers).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldCreateVolunteerFromVolunteerDtoIn() {
        var requestBody = new VolunteerDtoIn(
                888888L,
                "Volunteer",
                "Алексей",
                "Алексеев",
                1L
        );
        var expected = new VolunteerDtoOut(
                888888L,
                "Volunteer",
                "Алексей",
                "Алексеев",
                shelter
        );

        var actualResult = volunteerService.create(requestBody);
        assertThat(actualResult).isEqualTo(expected);

        var volunteers = volunteerService.findAll();
        assertThat(volunteers).contains(actualResult);
    }

    @Test
    void shouldUpdateVolunteer() {
        var updatedVolunteerIn = new VolunteerDtoIn(
                666666L,
                "Dimon123",
                "Дмитрий123",
                "Дмитриев123",
                1L
        );
        var actualResult = volunteerService.update(ID, updatedVolunteerIn);
        assertThat(actualResult.isPresent()).isTrue();

        var updatedVolunteerOut = new VolunteerDtoOut(
                666666L,
                "Dimon123",
                "Дмитрий123",
                "Дмитриев123",
                shelter
        );
        actualResult.ifPresent(actual ->
                assertThat(actual).isEqualTo(updatedVolunteerOut)
        );

        var volunteers = volunteerService.findAll();
        assertThat(volunteers).contains(updatedVolunteerOut);
    }

    @Test
    void shouldReturnTrueWhenDeleteIfVolunteerIsPresent() {
        var delete = volunteerService.delete(ID);
        assertThat(delete).isTrue();

        var volunteer = volunteerService.findById(ID);
        assertThat(volunteer.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeleteIfVolunteerIsNotPresent() {
        var delete = volunteerService.delete(0L);
        assertThat(delete).isFalse();
    }

}