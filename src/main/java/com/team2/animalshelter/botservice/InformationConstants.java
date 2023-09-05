package com.team2.animalshelter.botservice;

public class InformationConstants {

    public static final String FAQ_COMMAND = """
            Вот что я могу тебе рассказать:
                        
                Общие темы:
                /datingRules - правила знакомства с животным до того, как забрать его из приюта
                /docList - список документов, необходимых для того, чтобы взять животное из приюта
                /denialReasons - список причин, почему могут отказать в опеке над животным
                
                Кошки:
                /catTransportation - список рекомендаций по транспортировке кошки
                /homeForKitty - список рекомендаций по обустройству дома для котенка
                /homeForAdultCat - список рекомендаций по обустройству дома для взрослой кошки
                /homeForDisabledCat - список рекомендаций по обустройству дома для кошки с ограниченными возможностями
                
                Собаки:
                /dogTransportation - список рекомендаций по транспортировке собаки
                /homeForPuppy - список рекомендаций по обустройству дома для щенка
                /homeForAdultDog - список рекомендаций по обустройству дома для взрослой собаки
                /homeForDisabledDog - список рекомендаций по обустройству дома для собаки с ограниченными возможностями
                /cynologistAdvice - советы кинолога по первичному общению с собакой
                /provenCynologists - проверенные кинологи для дальнейшего обращения
            """;
    public static final String UNKNOWN_COMMAND = "Данная команда пока не поддерживается \uD83D\uDE14";

    public static final String CALL_VOLUNTEER = "Запрос принят, как только освободится волонтер, он сразу же Вам ответит \uD83D\uDE0A";
}
