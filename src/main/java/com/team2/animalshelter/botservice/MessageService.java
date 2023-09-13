package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team2.animalshelter.botservice.InformationConstants.*;

/**
 * Класс для отправки сообщений пользователю
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final TelegramBot telegramBot;

    /**
     * Отправка списка всех доступных команд в разделе FAQ.
     *
     * @param chatId указать в какой чат отправить сообщение.
     */
    public void sendFaqMessage(Long chatId) {
        sendMessage(chatId, FAQ_COMMAND);
    }

    private void sendMessage(Long chatId, String information) {
        var message = new SendMessage(chatId, information);
        telegramBot.execute(message);
    }

}
