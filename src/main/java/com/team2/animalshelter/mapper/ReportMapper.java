package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.ReportDtoIn;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.exception.AdaptationNotFoundException;
import com.team2.animalshelter.repository.AdaptationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReportMapper {

    private final AdaptationRepository adaptationRepository;
    private final AdaptationMapper adaptationMapper;

    public Report toEntity(ReportDtoIn fromObj) {
        var toObj = new Report();
        copy(fromObj, toObj);
        return toObj;
    }

    public Report toEntity(ReportDtoIn fromObj, Report toObj) {
        copy(fromObj, toObj);
        return toObj;
    }

    public ReportDtoOut toDto(Report fromObject) {
        return new ReportDtoOut(
                fromObject.getId(),
                fromObject.getReportMessage(),
                fromObject.getPhotoUrl(),
                fromObject.getDate(),
                adaptationMapper.toDto(fromObject.getAdaptation())
        );
    }

    private void copy(ReportDtoIn fromObj, Report toObj) {
        toObj.setReportMessage(fromObj.getReportMessage());
        toObj.setDate(LocalDate.now());
        toObj.setAdaptation(
                adaptationRepository.findById(fromObj.getAdaptationId())
                        .orElseThrow(() -> new AdaptationNotFoundException(fromObj.getAdaptationId()))
        );
    }

}
