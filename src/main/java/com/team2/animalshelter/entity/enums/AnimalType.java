package com.team2.animalshelter.entity.enums;

public enum AnimalType {
    CAT("Кошка"), DOG("Собака");

    private final String typeOfAnimal;

    AnimalType(String typeOfAnimal) {
        this.typeOfAnimal=typeOfAnimal;
    }

    public String getTypeOfAnimal() {
        return typeOfAnimal;
    }
}
