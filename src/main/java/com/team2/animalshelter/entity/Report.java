package com.team2.animalshelter.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
@Table(name = "report")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String message;

    String photo;

    LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adaptation_id", nullable = false)
    Adaptation adaptation;
}
