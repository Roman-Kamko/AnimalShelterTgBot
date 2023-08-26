package com.team2.animalshelter.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@Table(name = "report")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {
    Long id;
    String message;
    String photo;
    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adaptation", nullable = false)
    Adaptation adaptation;
}
