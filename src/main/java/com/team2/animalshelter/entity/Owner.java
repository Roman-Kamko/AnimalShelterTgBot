package com.team2.animalshelter.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Owner {

    @Id
    Long telegramId;

    String username;

    String firstname;

    String lastname;

    String phoneNumber;

    @OneToMany(mappedBy = "owner")
    List<Adaptation> adaptations;

}
