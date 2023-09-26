package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.mapper.AdaptationMapper;
import com.team2.animalshelter.repository.AdaptationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdaptationService {

    private final AdaptationRepository adaptationRepository;
    private final AdaptationMapper adaptationMapper;

    public Optional<AdaptationDtoOut> findById(Long id) {
        return adaptationRepository.findById(id)
                .map(adaptationMapper::toDto);
    }

    public List<AdaptationDtoOut> findAll() {
        return adaptationRepository.findAll().stream()
                .map(adaptationMapper::toDto)
                .collect(toList());
    }

    /**
     * Найти {@link Adaptation} с нужными статусами.
     *
     * @param statuses {@link AdaptationStatus}
     * @return {@code List<AdaptationDtoOut>}.
     */
    public List<AdaptationDtoOut> findAllByAdaptationStatus(AdaptationStatus ...statuses) {
        return adaptationRepository.findAllByAdaptationStatus(statuses).stream()
                .map(adaptationMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public AdaptationDtoOut create(AdaptationDtoIn adaptationDtoIn) {
        return Optional.of(adaptationDtoIn)
                .map(adaptationMapper::toEntity)
                .map(adaptationRepository::save)
                .map(adaptationMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public Optional<AdaptationDtoOut> update(Long id, AdaptationDtoIn adaptationDtoIn) {
        return adaptationRepository.findById(id)
                .map(adaptation -> adaptationMapper.toEntity(adaptationDtoIn, adaptation))
                .map(adaptationRepository::saveAndFlush)
                .map(adaptationMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return adaptationRepository.findById(id)
                .map(adaptation -> {
                    adaptationRepository.delete(adaptation);
                    adaptationRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public LocalDate findLastReportDate(Long ownerId) {
        return adaptationRepository.findLastReportDate(ownerId).stream()
                .findFirst()
                .map(Report::getDate)
                .orElse(null);
    }

}
