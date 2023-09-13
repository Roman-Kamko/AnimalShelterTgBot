package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.service.ShelterService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static com.team2.animalshelter.botservice.InformationConstants.*;

/**
 * Класс для отправки сообщений пользователю
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;

    /**
     * Отправка списка всех доступных команд в разделе FAQ.
     *
     * @param chatId указать в какой чат отправить сообщение.
     */
    public void sendFaqMessage(Long chatId) {
        sendMessage(chatId, FAQ_COMMAND);
    }

    /**
     * Отправка сообщения в случае не поддерживаемой команды.
     *
     * @param chatId указать в какой чат отправить сообщение.
     */
    public void sendUnknownCommand(Long chatId) {
        sendMessage(chatId, UNKNOWN_COMMAND);
    }

    private void sendMessage(Long chatId, String information) {
        var message = new SendMessage(chatId, information);
        telegramBot.execute(message);
    }

    public void sendShelterContact(Long chatId) {
        //"Общий телефон: +7-999-99-99\n" + "Телефон охраны для оформления пропуска: +7-888-88-88"
        String telephone = shelterService.findById(1L)
                .map(ShelterDtoOut::getPhoneNumber)
                .orElse(null);
        var message = new SendMessage(chatId,telephone);
        telegramBot.execute(message);
    }

    @SneakyThrows
    public void sendShelterAddress(Long chatId) {
        //"г. Астана, ул. Лесная, д. 3."
        String time = shelterService.findById(1L)
                .map(ShelterDtoOut::getAddress)
                .orElse(null);

        var message = new SendMessage(chatId, time);
        telegramBot.execute(message);

        File image = ResourceUtils.getFile("image/shelters/address.jpg");
        SendPhoto sendPhoto = new SendPhoto(chatId,image);
        telegramBot.execute(sendPhoto);


    }


    public void sendTimeTable(Long chatId) {
        //"Часы работы: Пн-Пт 08:00 - 20:00"
        String time = shelterService.findById(1L)
                .map(ShelterDtoOut::getTimeTable)
                .orElse(null);

        var message = new SendMessage(chatId, time);
        telegramBot.execute(message);
    }

    public void sendSafetyPrecautions(Long chatId) {
        sendMessage(chatId, SAFETY_PRECAUTIONS);
    }

    public void sendContact(Long chatId) {
        var message = new SendMessage(chatId, "Введите свой номер телефона ");
        telegramBot.execute(message);
    }

    public void answerContact(Long chatId) {
        var message = new SendMessage(chatId,"Ваш телефон принят. Скоро с Вами свяжется волонтер");
        telegramBot.execute(message);
    }

    public void wrongContact(Long chatId) {
        var message = new SendMessage(chatId, "Неправильно введен номер телефона");
        telegramBot.execute(message);
    }


}
