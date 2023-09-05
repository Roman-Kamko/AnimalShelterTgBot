package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Класс для обработки Update'ов
 */
@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;
    private final NavigationCommandHandler navigationCommandHandler;

    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            Chat chat = message.chat();
            Long chatId = message.chat().id();
            String text = message.text();

            if (userService.findById(chatId).isEmpty()) {
                userService.create(chat);
                keyboardService.sendGreetings(chatId);
                return;
            }

            if (text != null) {
                navigationCommandHandler.handle(text, chat);
            }
        }
    }

}
