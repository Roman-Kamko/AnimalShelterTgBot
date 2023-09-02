package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.team2.animalshelter.constant.ButtonKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team2.animalshelter.constant.ButtonKey.START;


import static com.team2.animalshelter.constant.ButtonKey.*;

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

    public void sendGreetings(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(START));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, "Привет");

    }

    public void sendMainMenu(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(SHELTER_MENU),
                new KeyboardButton(FAQ)
        ).addRow(
                new KeyboardButton(SEND_REPORT_FORM),
                new KeyboardButton(CALL_A_VOLUNTEER)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, CHOOSE);
    }

    public void sendChooseAnimalMenu(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(CAT_SHELTER),
                new KeyboardButton(DOG_SHELTER)
        ).addRow(
                new KeyboardButton(SHELTER_MENU)
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, CHOOSE);
    }

}
