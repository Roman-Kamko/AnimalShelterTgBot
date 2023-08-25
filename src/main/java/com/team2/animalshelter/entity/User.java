package com.team2.animalshelter.entity;

import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.entity.enums.UserStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    Long chatId;

    String userName;

    AnimalType animalType;

    String name;

    Integer age;

    String address;

    String phoneNumber;

    UserStatus userStatus;

    @OneToMany(mappedBy = "user")
    List<Animal> animals;

    @OneToMany(mappedBy = "user")
    List<Adaptation> adaptations;
}
