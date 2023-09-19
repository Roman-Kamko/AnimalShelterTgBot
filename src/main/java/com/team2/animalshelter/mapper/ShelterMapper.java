package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.Shelter;
import org.springframework.stereotype.Component;

@Component
public class ShelterMapper {

    /**
     * Метод позволяет внести изменения в сущность не создавая новую.
     *
     * @param fromObj объект копирования значений.
     * @param toObj объект для вставки значений.
     * @return измененный объект.
     */
    public Shelter toEntity(ShelterDtoIn fromObj, Shelter toObj) {
        toObj.setName(fromObj.getName());
        toObj.setAddress(fromObj.getAddress());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
        toObj.setTimeTable(fromObj.getTimeTable());
        return toObj;
    }

    public ShelterDtoOut toDto(Shelter fromObj) {
        return new ShelterDtoOut(
                fromObj.getName(),
                fromObj.getAddress(),
                fromObj.getPhoneNumber(),
                fromObj.getTimeTable(),
                fromObj.getDrivingDirections(),
                fromObj.getDrivingDirectionsUrl()
        );
    }

}
