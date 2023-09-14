package com.team2.animalshelter.botservice;

public enum FaqCommand {

    RULES("/datingRules"),
    DOC_LIST("/docList"),
    DENIAL_REASONS("/denialReasons"),
    CAT_TRANSPORTATION("/catTransportation"),
    HOME_FOR_KITTY("/homeForKitty"),
    HOME_FOR_ADULT_CAT("/homeForAdultCat"),
    HOME_FOR_DIS_CAT("/homeForDisabledCat"),
    DOG_TRANSPORTATION("/dogTransportation"),
    HOME_FOR_PUPPY("/homeForPuppy"),
    HOME_FOR_ADULT_DOG("/homeForAdultDog"),
    HOME_FOR_DIS_DOG("/homeForDisabledDog"),
    CYNOLOGIST_ADVISE("/cynologistAdvice"),
    PROVEN_CYNOLOGISTS("/provenCynologists");


    private final String text;

    FaqCommand(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
