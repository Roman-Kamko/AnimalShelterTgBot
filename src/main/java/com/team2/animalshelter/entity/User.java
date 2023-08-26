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
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    Long chatId;

    String userName;

    @Enumerated(EnumType.STRING)
    AnimalType animalType;

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
