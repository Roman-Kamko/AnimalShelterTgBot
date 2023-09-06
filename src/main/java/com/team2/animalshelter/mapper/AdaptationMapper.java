package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.in.AdaptationDtoIn;
import com.team2.animalshelter.entity.Adaptation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdaptationMapper {

    public Adaptation toEntity(AdaptationDtoIn adaptationDtoIn) {
        var adaptation = new Adaptation();
        return adaptation;
    }

}
