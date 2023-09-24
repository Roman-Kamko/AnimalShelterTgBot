package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Класс для обработки Update'ов
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateHandler {

    private final CommandHandler commandHandler;
    private final PhoneNumberHandler phoneNumberHandler;
    private final ReportHandler reportHandler;



    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            Chat chat = message.chat();
            String text = message.text();

            if (text != null) {
                if (text.startsWith("+")) {
                    phoneNumberHandler.handle(text, chat);
                } else {
                    commandHandler.handle(text, message);
                }
            }
            if (message.photo() != null) {
                reportHandler.handle(message);
            }
        }
    }


}
