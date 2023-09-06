package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.Shelter;
import org.springframework.stereotype.Component;

@Component
public class ShelterMapper {

    public Shelter toEntity(ShelterDtoIn fromObj) {
        var toObj = new Shelter();
        copy(fromObj, toObj);
        return toObj;
    }

    public Shelter toEntity(ShelterDtoIn fromObj, Shelter toObj) {
        copy(fromObj, toObj);
        return toObj;
    }

    private static void copy(ShelterDtoIn fromObj, Shelter toObj) {
        toObj.setName(fromObj.getName());
        toObj.setAddress(fromObj.getAddress());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
        toObj.setTimeTable(fromObj.getTimeTable());
        toObj.setDrivingDirections(fromObj.getDrivingDirections());
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
