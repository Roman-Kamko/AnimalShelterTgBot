package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.entity.Animal;
import com.team2.animalshelter.exception.AnimalAlreadyAdoptedException;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import com.team2.animalshelter.exception.OwnerNotFoundException;
import com.team2.animalshelter.repository.AnimalRepository;
import com.team2.animalshelter.repository.OwnerRepository;
import com.team2.animalshelter.service.AnimalService;
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
    private final AnimalService animalService;
    /**
     * Длительность адаптационного периода.
     */
    private final int duration = 30;

    //todo проверить
    public Adaptation toEntity(AdaptationDtoIn fromObj) {
        var toObj = new Adaptation();
        toObj.setStartDate(LocalDate.now());
        toObj.setEndDate(LocalDate.now().plusDays(duration));
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
        toObj.setAnimal(verifyAnimal(fromObj.getAnimalId()));
        toObj.setOwner(
                ownerRepository.findById(fromObj.getOwnerId())
                        .orElseThrow(() -> new OwnerNotFoundException(fromObj.getOwnerId()))
        );
    }

    /**
     * Проверка, что животное еще не взято под опеку.
     *
     * @param animalId идентификатор животного.
     * @return {@link Animal} в случае если id животного существует в базе и еще не взято под опеку.
     * @throws AnimalNotFoundException выбрасывается если животного нет в базе.
     * @throws AnimalAlreadyAdoptedException выбрасывается если животное уже взято под опеку.
     */
    private Animal verifyAnimal(Long animalId) {
        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new AnimalNotFoundException(animalId));
        var adopted = animalService.isAdopted(animalId);
        if (adopted) {
            throw new AnimalAlreadyAdoptedException(animalId);
        } else {
            return animal;
        }
    }

}
