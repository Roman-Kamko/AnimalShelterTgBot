package com.team2.animalshelter.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {

    @Id
    Long telegramId;

    String username;

    String firstname;

    String lastname;

}
