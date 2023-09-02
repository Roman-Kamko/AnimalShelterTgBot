package com.team2.animalshelter.entity;

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
@Table(name = "adaptation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Adaptation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDate endsDate;

    /**
     * Комментарий от волонтера при необходимости.
     */
    String comment;

    /**
     * Флаг для волонтеров в случае необходимости дать комментарий по адаптационному периоду.
     * Например: при длительной не отправке отчетов опекуном.
     *
     */
    Boolean isNeedComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id")
    Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @OneToMany(mappedBy = "adaptation")
    List<Report> reports;

}
