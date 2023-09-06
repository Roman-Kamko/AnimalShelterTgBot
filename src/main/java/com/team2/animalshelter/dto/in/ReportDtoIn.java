package com.team2.animalshelter.dto.in;

import com.team2.animalshelter.entity.Adaptation;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ReportDtoIn {

    String reportMessage;

    String photo;

    LocalDate date;

    Long adaptationId;

}
