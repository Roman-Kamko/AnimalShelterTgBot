package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import com.team2.animalshelter.exception.OwnerNotFoundException;
import com.team2.animalshelter.repository.AnimalRepository;
import com.team2.animalshelter.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdaptationMapper {

    private final AnimalRepository animalRepository;
    private final OwnerRepository ownerRepository;
    private final AnimalMapper animalMapper;
    private final OwnerMapper ownerMapper;

    //todo проверить
    public Adaptation toEntity(AdaptationDtoIn fromObj) {
        var toObj = new Adaptation();
        toObj.setStartDate(LocalDate.now());
        toObj.setEndDate(LocalDate.now().plusDays(30));
        copy(fromObj, toObj);
        return toObj;
    }

    /**
     * Метод позволяет внести изменения в сущность не создавая новую.
     *
     * @param fromObj объект копирования значений.
     * @param toObj   объект для вставки значений.
     * @return измененный объект.
     */
    public Adaptation toEntity(AdaptationDtoIn fromObj, Adaptation toObj) {
        copy(fromObj, toObj);
        return toObj;
    }

    public AdaptationDtoOut toDto(Adaptation fromObj) {
        return new AdaptationDtoOut(
                fromObj.getId(),
                fromObj.getStartDate(),
                fromObj.getEndDate(),
                fromObj.getComment(),
                fromObj.getProblem(),
                fromObj.getAdaptationStatus(),
                animalMapper.toDto(fromObj.getAnimal()),
                ownerMapper.toDto(fromObj.getOwner())
        );
    }

    private void copy(AdaptationDtoIn fromObj, Adaptation toObj) {
        toObj.setComment(fromObj.getAdaptationStatus().getDescription());
        toObj.setAdaptationStatus(fromObj.getAdaptationStatus());
        toObj.setAnimal(
                animalRepository.findById(fromObj.getAnimalId())
                        .orElseThrow(() -> new AnimalNotFoundException(fromObj.getAnimalId()))
        );
        toObj.setOwner(
                ownerRepository.findById(fromObj.getOwnerId())
                        .orElseThrow(() -> new OwnerNotFoundException(fromObj.getOwnerId()))
        );
    }

}
