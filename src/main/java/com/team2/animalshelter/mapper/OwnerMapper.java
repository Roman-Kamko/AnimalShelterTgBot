package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    /**
     * Для создания Опекуна на основе данных Пользователя.
     *
     * @param fromObj {@link UserDto}.
     * @return {@link Owner}.
     */
    public Owner toEntity(UserDto fromObj) {
        var toObj = new Owner();
        toObj.setTelegramId(fromObj.getTelegramId());
        toObj.setUsername(fromObj.getUsername());
        toObj.setFirstname(fromObj.getFirstname());
        toObj.setLastname(fromObj.getLastname());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
        return toObj;
    }

    /**
     * Метод позволяет внести изменения в сущность не создавая новую.
     *
     * @param fromObj объект копирования значений.
     * @param toObj объект для вставки значений.
     * @return измененный объект.
     */
    public Owner toEntity(OwnerDto fromObj, Owner toObj) {
        toObj.setTelegramId(fromObj.getTelegramId());
        toObj.setUsername(fromObj.getUsername());
        toObj.setFirstname(fromObj.getFirstname());
        toObj.setLastname(fromObj.getLastname());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
        return toObj;
    }

    public OwnerDto toDto(Owner fromObj) {
        return new OwnerDto(
                fromObj.getTelegramId(),
                fromObj.getUsername(),
                fromObj.getFirstname(),
                fromObj.getLastname(),
                fromObj.getPhoneNumber()
        );
    }

}
