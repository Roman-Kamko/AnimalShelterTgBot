package com.team2.animalshelter.botservice;

public enum NavigationCommand {

    START("/start"),
    MAIN_MENU("Главное меню"),
    SHELTER_MENU("Меню приюта"),
    FAQ("ЧаВо"),
    CHOOSE_ANIMAL_TYPE("Выбрать питомца"),
    CALL_A_VOLUNTEER("Позвать волонтёра"),
    SEND_REPORT_FORM("Отправить отчёт"),
    CAT_SHELTER("Приют для кошек"),
    DOG_SHELTER("Приют для собак"),
    SHELTER_CONTACT("Контакты"),
    SHELTER_ADDRESS("Адрес"),
    TIME_TABLE("Расписание"),
    SAFETY_PRECAUTIONS("Техника безопасности"),
    SEND_CONTACT("Оставить телефон");
    private final String text;

    NavigationCommand(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
