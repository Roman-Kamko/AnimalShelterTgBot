package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDto fromObj) {
        var toObj = new User();
        toObj.setTelegramId(fromObj.getTelegramId());
        toObj.setUsername(fromObj.getUsername());
        toObj.setFirstname(fromObj.getFirstname());
        toObj.setLastname(fromObj.getLastname());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
        return toObj;
    }

    public UserDto toDto(User fromObj) {
        return new UserDto(
                fromObj.getTelegramId(),
                fromObj.getUsername(),
                fromObj.getFirstname(),
                fromObj.getLastname(),
                fromObj.getPhoneNumber()
        );
    }

}
