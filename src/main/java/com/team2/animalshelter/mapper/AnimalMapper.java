package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.in.AnimalDtoIn;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.Animal;
import com.team2.animalshelter.repository.ShelterRepository;
import com.team2.animalshelter.service.ShelterService;
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

    public Animal toEntity(AnimalDtoIn animalDtoIn) {
        Animal animal = new Animal();
        animal.setName(animal.getName());
        animal.setAge(animalDtoIn.getAge());
        animal.setBreed(animal.getBreed());
        animal.setHealthy(animal.getHealthy());
        animal.setAnimalType(animalDtoIn.getAnimalType());
        animal.setShelter(
                shelterRepository.findById(animalDtoIn.getShelterId())
                        .orElseThrow(RuntimeException::new)
        );
        return animal;
    }



    public AnimalDtoOut toDto(Animal animal) {
        return new AnimalDtoOut(
                animal.getId(),
                animal.getName(),
                animal.getAge(),
                animal.getBreed(),
                animal.getHealthy(),
                animal.getAnimalType(),
                shelterMapper.toDto(animal.getShelter())
        );
    }
}
