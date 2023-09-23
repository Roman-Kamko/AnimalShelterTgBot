package com.team2.animalshelter.mapper;

import com.pengrad.telegrambot.model.Message;
import com.team2.animalshelter.dto.in.ReportDtoIn;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.exception.AdaptationNotFoundException;
import com.team2.animalshelter.exception.OwnerAdaptationException;
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

    /**
     * Маппинг в {@link Report} на основе {@link Message}.
     *
     * @param fromObj {@link Message}
     * @return {@link Report}
     */
    public Report toEntity(Message fromObj) {
        var toObj = new Report();
        toObj.setPhoto(fromObj.photo()[fromObj.photo().length - 1].fileId());
        toObj.setReportMessage(fromObj.caption());
        toObj.setDate(LocalDate.now());
        var ownerId = fromObj.from().id();
        toObj.setAdaptation(
                adaptationRepository.findByOwner_telegramId(ownerId)
                        .orElseThrow(() -> new OwnerAdaptationException(ownerId))
        );
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
