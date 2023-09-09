package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.dto.out.VolunteerDtoOut;
import com.team2.animalshelter.entity.Volunteer;
import com.team2.animalshelter.exception.ShelterNotFoundException;
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
    final ShelterMapper shelterMapper;

    public Volunteer toEntity(VolunteerDtoIn fromObj) {
        var toObj = new Volunteer();
        toObj.setUsername(fromObj.getUsername());
        toObj.setFirstname(fromObj.getFirstname());
        toObj.setLastname(fromObj.getLastname());
        toObj.setShelter(
                shelterRepository.findById(fromObj.getShelterId())
                        .orElseThrow(() -> new ShelterNotFoundException(fromObj.getShelterId()))
        );
        return toObj;
    }

    public VolunteerDtoOut toDto(Volunteer fromObj) {
        return new VolunteerDtoOut(
                fromObj.getTelegramId(),
                fromObj.getUsername(),
                fromObj.getFirstname(),
                fromObj.getLastname(),
                shelterMapper.toDto(fromObj.getShelter())
        );
    }
}
