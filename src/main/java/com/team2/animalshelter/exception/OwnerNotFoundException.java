package com.team2.animalshelter.exception;

public class OwnerNotFoundException extends RuntimeException{

    private final long id;

    public OwnerNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Опекун с id: " + id +" не найден!";
    }

}
