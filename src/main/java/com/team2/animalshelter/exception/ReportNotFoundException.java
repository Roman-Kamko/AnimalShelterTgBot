package com.team2.animalshelter.exception;

public class ReportNotFoundException extends RuntimeException{

    private final long id;

    public ReportNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Отчет с id: " + id +" не найден!";
    }

}
