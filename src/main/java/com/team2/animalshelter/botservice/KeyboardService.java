package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final TelegramBot telegramBot;
    //    private final UserService userService;
    private static final String CHOOSE = "Выберите:";

    /**
     * Утилитный метод содержащий необходимые настройки для всех менюшек.
     *
     * @param replyKeyboardMarkup передать собранное меню
     * @param chatId              передать идентификатор чата.
     * @param text                передать текст, для всего кроме приветствия использовать {@link KeyboardService#CHOOSE}
     */
    private void returnResponseReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup, Long chatId, String text) {
        replyKeyboardMarkup.resizeKeyboard(true);
        replyKeyboardMarkup.oneTimeKeyboard(false);
        replyKeyboardMarkup.selective(false);
        var request = new SendMessage(chatId, text)
                .replyMarkup(replyKeyboardMarkup)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true);
        telegramBot.execute(request);
    }
}
