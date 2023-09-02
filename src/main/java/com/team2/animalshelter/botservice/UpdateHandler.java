package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team2.animalshelter.constant.ButtonKey.*;

/**
 * Класс для обработки Update'ов
 */
@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;

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
                switch (text) {
                    case START, MAIN_MENU -> keyboardService.sendMainMenu(chatId);
                    case SHELTER_MENU -> keyboardService.sendShelterMenu(chatId);
                    case CHOOSE_ANIMAL_TYPE -> keyboardService.sendChooseAnimalMenu(chatId);
                    case FAQ -> messageService.sendFaqMessage(chatId);
                    default -> messageService.sendUnknownCommand(chatId);
                }
            }

        }
    }

}
