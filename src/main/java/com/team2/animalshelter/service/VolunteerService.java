package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.in.VolunteerDtoIn;
import com.team2.animalshelter.dto.out.VolunteerDtoOut;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.mapper.VolunteerMapper;
import com.team2.animalshelter.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final VolunteerMapper volunteerMapper;

    public Optional<VolunteerDtoOut> findById(Long id) {
        return volunteerRepository.findById(id)
                .map(volunteerMapper::toDto);
    }

    public List<VolunteerDtoOut> findAll() {
        return volunteerRepository.findAll().stream()
                .map(volunteerMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public VolunteerDtoOut create(VolunteerDtoIn volunteerDtoIn) {
        return Optional.of(volunteerDtoIn)
                .map(volunteerMapper::toEntity)
                .map(volunteerRepository::save)
                .map(volunteerMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public Optional<VolunteerDtoOut> update(Long id, VolunteerDtoIn volunteerDtoIn) {
        return volunteerRepository.findById(id)
                .map(volunteer -> volunteerMapper.toEntity(volunteerDtoIn))
                .map(volunteerRepository::saveAndFlush)
                .map(volunteerMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return volunteerRepository.findById(id)
                .map(volunteer -> {
                    volunteerRepository.delete(volunteer);
                    volunteerRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
