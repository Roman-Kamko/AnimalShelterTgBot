package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.entity.Volunteer;
import com.team2.animalshelter.repository.ShelterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VolunteerMapper {

    final ShelterRepository shelterRepository;

    public Volunteer toEntity(VolunteerDtoIn volunteerDtoIn) {
        var volunteer = new Volunteer();
        volunteer.setUsername(volunteerDtoIn.getUsername());
        volunteer.setFirstname(volunteerDtoIn.getFirstname());
        volunteer.setLastname(volunteerDtoIn.getLastname());
        volunteer.setShelter(shelterRepository.findById(volunteerDtoIn.getShelterId()).orElseThrow(RuntimeException::new));
        return volunteer;
    }

}
