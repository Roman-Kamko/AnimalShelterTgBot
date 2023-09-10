package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.exception.ShelterNotFoundException;
import com.team2.animalshelter.mapper.ShelterMapper;
import com.team2.animalshelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;

    public ShelterDtoOut findById(Long id) {
        return shelterRepository.findById(id)
                .map(shelterMapper::toDto)
                .orElseThrow(() -> new ShelterNotFoundException(id));
    }

}
