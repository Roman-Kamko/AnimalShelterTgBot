package com.team2.animalshelter.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "volunteers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Volunteer {

    @Id
    Long Id;

    String firstname;

    String lastname;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    Shelter shelter;
}
