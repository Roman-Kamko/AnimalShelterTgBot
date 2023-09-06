package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.repository.AnimalRepository;
import com.team2.animalshelter.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdaptationMapper {

    private final AnimalRepository animalRepository;
    private final OwnerRepository ownerRepository;
    private final AnimalMapper animalMapper;
    private final OwnerMapper ownerMapper;

    public Adaptation toEntity(AdaptationDtoIn fromObj) {
        var toObj = new Adaptation();
        copy(fromObj, toObj);
        return toObj;
    }

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
                fromObj.getNeedComment(),
                fromObj.getAdaptationStatus(),
                animalMapper.toDto(fromObj.getAnimal()),
                ownerMapper.toDto(fromObj.getOwner())
        );
    }

    private void copy(AdaptationDtoIn fromObj, Adaptation toObj) {
        toObj.setComment(fromObj.getComment());
        toObj.setNeedComment(fromObj.getNeedComment());
        toObj.setAdaptationStatus(fromObj.getAdaptationStatus());
        toObj.setAnimal(
                animalRepository.findById(fromObj.getAnimalId())
                        .orElseThrow(RuntimeException::new)
        );
        toObj.setOwner(
                ownerRepository.findById(fromObj.getOwnerId())
                        .orElseThrow(RuntimeException::new)
        );
    }

}
