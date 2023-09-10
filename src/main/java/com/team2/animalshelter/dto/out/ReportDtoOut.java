package com.team2.animalshelter.dto.out;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ReportDtoOut {

    Long id;

    String reportMessage;

    String photo;

    LocalDate date;

    AdaptationDtoOut adaptation;

}
