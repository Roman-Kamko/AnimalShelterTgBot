package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.Shelter;
import org.springframework.stereotype.Component;

@Component
public class ShelterMapper {

    public Shelter toEntity(ShelterDtoIn shelterDtoIn) {
        var shelter = new Shelter();
        shelter.setName(shelterDtoIn.getName());
        shelter.setAddress(shelterDtoIn.getAddress());
        shelter.setPhoneNumber(shelterDtoIn.getPhoneNumber());
        shelter.setTimeTable(shelterDtoIn.getTimeTable());
        shelter.setDrivingDirections(shelterDtoIn.getDrivingDirections());
        return shelter;
    }

    public ShelterDtoOut toDto(Shelter shelter) {
        return new ShelterDtoOut(
                shelter.getId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getPhoneNumber(),
                shelter.getTimeTable(),
                shelter.getDrivingDirections()
        );
    }

}
