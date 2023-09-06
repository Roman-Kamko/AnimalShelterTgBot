package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.AnimalDtoIn;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.Animal;
import com.team2.animalshelter.repository.ShelterRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimalMapper {

    final ShelterRepository shelterRepository;
    final ShelterMapper shelterMapper;

    public Animal toEntity(AnimalDtoIn fromObj) {
        Animal toObj = new Animal();
        copy(fromObj, toObj);
        return toObj;
    }

    public Animal toEntity(AnimalDtoIn fromObj, Animal toObj) {
        copy(fromObj, toObj);
        return toObj;
    }

    public AnimalDtoOut toDto(Animal fromObj) {
        return new AnimalDtoOut(
                fromObj.getId(),
                fromObj.getName(),
                fromObj.getAge(),
                fromObj.getBreed(),
                fromObj.getHealthy(),
                fromObj.getAnimalType(),
                shelterMapper.toDto(fromObj.getShelter())
        );
    }

    private void copy(AnimalDtoIn fromObj, Animal toObj) {
        toObj.setName(fromObj.getName());
        toObj.setAge(fromObj.getAge());
        toObj.setBreed(fromObj.getBreed());
        toObj.setHealthy(fromObj.getHealthy());
        toObj.setAnimalType(fromObj.getAnimalType());
        toObj.setShelter(
                shelterRepository.findById(fromObj.getShelterId())
                        .orElseThrow(RuntimeException::new)
        );
    }
}
