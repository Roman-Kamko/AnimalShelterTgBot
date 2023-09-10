package com.team2.animalshelter.exception;

public class AdaptationNotFoundException extends RuntimeException{

    private final long id;

    public AdaptationNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Адаптационный период с id: " + id +" не найден!";
    }

}
