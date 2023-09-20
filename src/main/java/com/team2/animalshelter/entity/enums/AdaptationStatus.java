package com.team2.animalshelter.entity.enums;

public enum AdaptationStatus {
    IN_PROGRESS("В процессе"),
    SUCCESSFUL("Успешно завершен"),
    NOT_SUCCESSFUL("Провален"),
    EXTENDED("Продлен");

    private final String description;

    AdaptationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
