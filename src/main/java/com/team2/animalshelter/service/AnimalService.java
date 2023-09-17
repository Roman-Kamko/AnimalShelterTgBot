package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.in.AnimalDtoIn;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.exception.AnimalNotFoundException;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.mapper.AnimalMapper;
import com.team2.animalshelter.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    public Optional<AnimalDtoOut> findById(Long id) {
        return animalRepository.findById(id)
                .map(animalMapper::toDto);
    }

    /**
     * Найти всех животных, которых еще не взяли под опеку
     *
     * @param animalType тип животного
     * @return список животных
     */
    public List<AnimalDtoOut> findAllWithoutOwner(AnimalType animalType) {
        return animalRepository.findAllWithoutOwner(animalType).stream()
                .map(animalMapper::toDto)
                .collect(toList());
    }

    /**
     * Найти всех животных, которых взяли под опеку
     *
     * @param animalType тип животного
     * @return список животных
     */
    public List<AnimalDtoOut> findAllAdopted(AnimalType animalType) {
        return animalRepository.findAllAdopted(animalType).stream()
                .map(animalMapper::toDto)
                .collect(toList());
    }

    public boolean isAdopted(Long id) {
        var animal = findById(id);
        var animalType = animal
                .map(AnimalDtoOut::getAnimalType)
                .orElseThrow(() -> new AnimalNotFoundException(id));
        return findAllAdopted(animalType).contains(animal.get());
    }

    @Transactional
    public AnimalDtoOut create(AnimalDtoIn animalDtoIn) {
        return Optional.of(animalDtoIn)
                .map(animalMapper::toEntity)
                .map(animalRepository::save)
                .map(animalMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public Optional<AnimalDtoOut> update(Long id, AnimalDtoIn animalDtoIn) {
        return animalRepository.findById(id)
                .map(animal -> animalMapper.toEntity(animalDtoIn, animal))
                .map(animalRepository::saveAndFlush)
                .map(animalMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return animalRepository.findById(id)
                .map(animal -> {
                    animalRepository.delete(animal);
                    animalRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
