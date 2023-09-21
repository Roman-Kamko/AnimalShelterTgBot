package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team2.animalshelter.botservice.Command.*;

/**
 * Класс в котором строятся основные меню для навигации по боту
 */
@Service
@RequiredArgsConstructor
public class KeyboardService {

    private final TelegramBot telegramBot;

    /**
     * Утилитарный метод содержащий необходимые настройки для всех меню.
     *
     * @param replyKeyboardMarkup передать собранное меню
     * @param chatId              передать идентификатор чата.
     * @param text                передать текст, для всего кроме приветствия использовать {@link InformationConstants#CHOOSE}
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

    /**
     * Приветствие для пользователя еще не сохраненного в БД.
     *
     * @param chatId передать идентификатор чата.
     */
    public void sendGreetings(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(MAIN_MENU.getText()));
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, InformationConstants.GREETINGS);

    }

    /**
     * Главное меню.
     *
     * @param chatId передать идентификатор чата.
     */
    public void sendMainMenu(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(SHELTER_MENU.getText()),
                new KeyboardButton(FAQ.getText())
        ).addRow(
                new KeyboardButton(SEND_REPORT_FORM.getText()),
                new KeyboardButton(CALL_A_VOLUNTEER.getText())
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, InformationConstants.CHOOSE);
    }

    /**
     * Меню выбора типа животного.
     *
     * @param chatId передать идентификатор чата.
     */
    public void sendChooseAnimalMenu(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(CAT_SHELTER.getText()),
                new KeyboardButton(DOG_SHELTER.getText())
        ).addRow(
                new KeyboardButton(SHELTER_MENU.getText())
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, InformationConstants.CHOOSE);
    }

    /**
     * Меню приюта.
     *
     * @param chatId передать идентификатор чата.
     */
    public void sendShelterMenu(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton(CHOOSE_ANIMAL_TYPE.getText())
        ).addRow(
                new KeyboardButton(SHELTER_CONTACT.getText()),
                new KeyboardButton(SHELTER_ADDRESS.getText()),
                new KeyboardButton(TIME_TABLE.getText())
        ).addRow(
                new KeyboardButton(SAFETY_PRECAUTIONS.getText()),
                new KeyboardButton(SEND_CONTACT.getText())
        ).addRow(
                new KeyboardButton(CALL_A_VOLUNTEER.getText()),
                new KeyboardButton(MAIN_MENU.getText())
        );
        returnResponseReplyKeyboardMarkup(replyKeyboardMarkup, chatId, InformationConstants.CHOOSE);
    }

}
