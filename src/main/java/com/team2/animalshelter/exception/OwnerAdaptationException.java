package com.team2.animalshelter.exception;

public class OwnerAdaptationException extends RuntimeException {

    private final long id;

    public OwnerAdaptationException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "У опекун с id: " + id + " нет активной адаптации";
    }

}
