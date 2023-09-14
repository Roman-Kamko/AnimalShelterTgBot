package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
