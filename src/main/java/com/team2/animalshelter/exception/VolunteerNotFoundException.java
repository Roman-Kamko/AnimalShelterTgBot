package com.team2.animalshelter.exception;

public class VolunteerNotFoundException extends RuntimeException{

    private final long id;

    public VolunteerNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Волонтер с id: " + id +" не найден!";
    }

}
