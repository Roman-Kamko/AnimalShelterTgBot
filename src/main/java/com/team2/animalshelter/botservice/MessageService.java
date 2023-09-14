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

/**
 * Класс для отправки сообщений пользователю
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final TelegramBot telegramBot;
    private final ShelterService shelterService;

    public void sendMessage(Long chatId, String text) {
        var message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    public void sendShelterContact(Long chatId) {
        var phone = shelterService.findById(1L)
                .map(ShelterDtoOut::getPhoneNumber)
                .orElse(null);
        sendMessage(chatId, phone);
    }

    @SneakyThrows
    public void sendShelterAddress(Long chatId) {
        var address = shelterService.findById(1L)
                .map(ShelterDtoOut::getAddress)
                .orElse(null);
        sendMessage(chatId, address);
        var image = ResourceUtils.getFile("image/shelters/address.jpg");
        SendPhoto sendPhoto = new SendPhoto(chatId, image);
        telegramBot.execute(sendPhoto);
    }

    public void sendTimeTable(Long chatId) {
        var timeTable = shelterService.findById(1L)
                .map(ShelterDtoOut::getTimeTable)
                .orElse(null);
        sendMessage(chatId, timeTable);
    }

    public void sendSafetyPrecautions(Long chatId) {
        sendMessage(chatId, InformationConstants.SAFETY_PRECAUTIONS);
    }

    public void sendContact(Long chatId) {
        sendMessage(chatId, "Введите свой номер телефона в формате +79998886655:");
    }

    public void answerContact(Long chatId) {
        sendMessage(chatId, "Ваш телефон принят");
    }

    public void wrongContact(Long chatId) {
        sendMessage(chatId, "Неправильно введен номер телефона");
    }

}
