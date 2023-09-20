package com.team2.animalshelter.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"animals", "volunteers", "users"})
@ToString(exclude = {"animals", "volunteers", "users"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String address;

    String phoneNumber;

    String timeTable;

    String drivingDirections;

    String drivingDirectionsUrl;

    @OneToMany(mappedBy = "shelter")
    List<Animal> animals;

    @OneToMany(mappedBy = "shelter")
    List<Volunteer> volunteers;

    @OneToMany(mappedBy = "shelter")
    List<User> users;

}
