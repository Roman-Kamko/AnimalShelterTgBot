package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.dto.out.VolunteerDtoOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class VolunteerServiceTest extends IntegrationTestBase {

    @Autowired
    private VolunteerService volunteerService;
    private VolunteerDtoIn volunteerDtoIn;
    private VolunteerDtoOut volunteerDtoOut;
    private ShelterDtoOut shelterDtoOut;
    private static final long ID = 666666L;

    @BeforeEach
    void beforeEach() {
        volunteerDtoIn = new VolunteerDtoIn(
                666666L,
                "Dimon",
                "Дмитрий",
                "Дмитриев",
                1L
        );
        shelterDtoOut = new ShelterDtoOut(
                1L,
                "КотоПес",
                "г. Астана, ул. Лесная, д. 3.",
                "Телефон охраны для оформления пропуска: +7-888-88-88; Общий телефон: +7-999-99-99",
                "Часы работы: Пн-Пт 08:00 - 20:00",
                "address.jpg",
                "http://localhost:8081/api/v1/shelters/3/map"
        );
        volunteerDtoOut = new VolunteerDtoOut(
                666666L,
                "Dimon",
                "Дмитрий",
                "Дмитриев",
                shelterDtoOut
        );
    }

    @Test
    void shouldReturnOptionalOfVolunteerWhenFindById() {
        var maybeVolunteer = volunteerService.findById(ID);
        assertThat(maybeVolunteer.isPresent()).isTrue();
        maybeVolunteer.ifPresent(volunteer ->
                assertThat(volunteer).isEqualTo(volunteerDtoOut)
        );
    }

    @Test
    void shouldReturn2VolunteersWhenFindAll() {
        var volunteers = volunteerService.findAll();
        assertThat(volunteers).isNotEmpty().hasSize(2);
    }

    @Test
    void shouldCreateVolunteerFromVolunteerDtoIn() {
        var volunteer = volunteerService.create(volunteerDtoIn);
        assertThat(volunteer).isEqualTo(volunteerDtoOut);

        var volunteers = volunteerService.findAll();
        assertThat(volunteers).contains(volunteer);
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
                shelterDtoOut
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
        var delete = volunteerService.delete(1L);
        assertThat(delete).isFalse();
    }

}