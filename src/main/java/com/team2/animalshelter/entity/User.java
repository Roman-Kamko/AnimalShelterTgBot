package com.team2.animalshelter.entity;

import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.entity.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"animals", "adaptations"})
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    Long id;

    String username;

    String name;

    Integer age;

    String address;

    String phoneNumber;

    @Enumerated(EnumType.STRING)
    UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    List<Animal> animals;

    @OneToMany(mappedBy = "user")
    List<Adaptation> adaptations;

}
