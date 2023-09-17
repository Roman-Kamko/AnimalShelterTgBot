package com.team2.animalshelter.exception;

public class AnimalNotFoundException extends RuntimeException{

    private final long id;

    public AnimalNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Животное с id: " + id +" не найдено!";
    }

}
