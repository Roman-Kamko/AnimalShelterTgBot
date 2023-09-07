package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public Owner toEntity(OwnerDto fromObj) {
        var toObj = new Owner();
        copy(fromObj, toObj);
        return toObj;
    }

    public Owner toEntity(OwnerDto fromObj, Owner toObj) {
        copy(fromObj, toObj);
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

    private void copy(OwnerDto fromObj, Owner toObj) {
        toObj.setTelegramId(fromObj.getTelegramId());
        toObj.setUsername(fromObj.getUsername());
        toObj.setFirstname(fromObj.getFirstname());
        toObj.setLastname(fromObj.getLastname());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
    }

}
