package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Класс для обработки Update'ов
 */
@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final NavigationCommandHandler navigationCommandHandler;

    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            Chat chat = message.chat();
            String text = message.text();

            if (text != null) {
                navigationCommandHandler.handle(text, chat);
            }
        }
    }

}
