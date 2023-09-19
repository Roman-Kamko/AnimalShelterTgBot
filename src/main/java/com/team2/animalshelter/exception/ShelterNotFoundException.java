package com.team2.animalshelter.exception;

public class ShelterNotFoundException extends RuntimeException{

    public ShelterNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "Приют не найден!";
    }

}
