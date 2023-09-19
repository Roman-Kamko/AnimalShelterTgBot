package com.team2.animalshelter.botservice;

/**
 * Список поддерживаемых команд
 */
public enum Command {

    START("/start"),
    MAIN_MENU("Главное меню"),
    SHELTER_MENU("Меню приюта"),
    FAQ("ЧаВо"),
    CHOOSE_ANIMAL_TYPE("Выбрать питомца"),
    CALL_A_VOLUNTEER("Позвать волонтёра"),
    SEND_REPORT_FORM("Отправить отчёт"),
    CAT_SHELTER("КОШКИ"),
    DOG_SHELTER("СОБАКИ"),
    SHELTER_CONTACT("Контакты"),
    SHELTER_ADDRESS("Адрес"),
    TIME_TABLE("Расписание"),
    SAFETY_PRECAUTIONS("Техника безопасности"),
    SEND_CONTACT("Оставить телефон"),
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

    Command(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
