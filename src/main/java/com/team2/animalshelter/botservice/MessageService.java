package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.team2.animalshelter.constant.Information;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        var message = new SendMessage(chatId, Information.FAQ_COMMAND);
        telegramBot.execute(message);
    }

    /**
     * Отправка сообщения в случае не поддерживаемой команды.
     *
     * @param chatId указать в какой чат отправить сообщение.
     */
    public void sendUnknownCommand(Long chatId) {
        var message = new SendMessage(chatId, Information.UNKNOWN_COMMAND);
        telegramBot.execute(message);
    }

}
