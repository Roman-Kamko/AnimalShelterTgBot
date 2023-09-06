package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.entity.Volunteer;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {

    public Volunteer toEntity(VolunteerDtoIn volunteerDtoIn) {
        var volunteer = new Volunteer();
        volunteer.setUsername(volunteerDtoIn.getUsername());
        volunteer.setFirstname(volunteerDtoIn.getFirstname());
        volunteer.setLastname(volunteerDtoIn.getLastname());
        volunteer.setShelter();
        return volunteer;
    }

}
