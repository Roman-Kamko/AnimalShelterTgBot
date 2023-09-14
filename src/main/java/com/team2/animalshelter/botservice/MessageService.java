package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
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


    public void sendMessage(Long chatId, String text) {
        var message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    @SneakyThrows
    public void sendPhoto(Long chatId, String path) {
        var image = ResourceUtils.getFile(path);
        SendPhoto sendPhoto = new SendPhoto(chatId, image);
        telegramBot.execute(sendPhoto);
    }

}
