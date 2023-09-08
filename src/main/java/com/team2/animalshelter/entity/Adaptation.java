package com.team2.animalshelter.entity;

import com.team2.animalshelter.entity.enums.AdaptationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Адаптационный период животного взятого под опеку.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Adaptation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate startDate;

    LocalDate endDate;

    /**
     * Описание статуса
     */
    String comment;

    /**
     * Флаг для волонтеров в случае при длительной не отправке отчетов опекуном.
     */
    Boolean problem;

    @Enumerated(EnumType.STRING)
    AdaptationStatus adaptationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    Owner owner;

    @OneToMany(mappedBy = "adaptation")
    List<Report> reports;

}
