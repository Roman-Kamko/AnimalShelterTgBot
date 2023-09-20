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
    private static final long ID = 666666L;

    @Test
    void shouldReturnOptionalOfVolunteerWhenFindById() {
        var actualResult = volunteerService.findById(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new VolunteerDtoOut(
                666666L,
                "Dimon",
                "Дмитрий",
                "Дмитриев"
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
                "Алексеев"
        );
        var expected = new VolunteerDtoOut(
                888888L,
                "Volunteer",
                "Алексей",
                "Алексеев"
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
                "Дмитриев123"
        );
        var actualResult = volunteerService.update(ID, updatedVolunteerIn);
        assertThat(actualResult.isPresent()).isTrue();

        var updatedVolunteerOut = new VolunteerDtoOut(
                666666L,
                "Dimon123",
                "Дмитрий123",
                "Дмитриев123"
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