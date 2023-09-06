package com.team2.animalshelter.mapper;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.entity.Owner;

public class OwnerMapper {

    public Owner toEntity(OwnerDto fromDto) {
        Owner toObj = new Owner();
        copy(fromDto, toObj);
        return toObj;
    }

    public Owner toEntity(OwnerDto fromObj, Owner toObj) {
        copy(fromObj, toObj);
        return toObj;
    }

    private static void copy(OwnerDto fromObj, Owner toObj) {
        toObj.setTelegramId(fromObj.getTelegramId());
        toObj.setUsername(fromObj.getUsername());
        toObj.setFirstname(fromObj.getFirstname());
        toObj.setLastname(fromObj.getLastname());
        toObj.setPhoneNumber(fromObj.getPhoneNumber());
    }

    public OwnerDto toDto(Owner owner) {
        return new OwnerDto(
                owner.getTelegramId(),
                owner.getUsername(),
                owner.getFirstname(),
                owner.getLastname(),
                owner.getPhoneNumber()
        );
    }
}
