package com.team2.animalshelter.exception;

public class AnimalAlreadyAdoptedException extends RuntimeException {

    private final long id;

    public AnimalAlreadyAdoptedException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Животное с id: " + id +" уже взято под опеку!";
    }

}
