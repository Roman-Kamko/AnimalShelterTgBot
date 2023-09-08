package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;

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

    /**
     * Отправка сообщения в случае не поддерживаемой команды.
     *
     * @param chatId указать в какой чат отправить сообщение.
     */
    public void sendUnknownCommand(Long chatId) {
        sendMessage(chatId, UNKNOWN_COMMAND);
    }

    private void sendMessage(Long chatId, String information) {
        var message = new SendMessage(chatId, information);
        telegramBot.execute(message);
    }

    public void sendShelterContact(Long chatId) {
        var message = new SendMessage(chatId, "Общий телефон: +7-999-99-99\n" +
                "Телефон охраны для оформления пропуска: +7-888-88-88");
        telegramBot.execute(message);
    }

    @SneakyThrows
    public void sendShelterAddress(Long chatId) {
        var message1 = new SendMessage(chatId, "г. Астана, ул. Лесная, д. 3.");
        //File image= ResourceUtils.getFile("src/main/resources/address.jpg");
        //var message2 = new SendPhoto(chatId,"src/main/resources/address.jpg");
        //message2.caption(imageCaption);

        telegramBot.execute(message1);
        //telegramBot.execute(message2);
       /* File image= ResourceUtils.getFile("src/main/resources/address.jpg");
SendPhoto sendPhoto=new SendPhoto().;//.setPhoto(image);
sendPhoto.setChatId(chatId)*/
        String imagePath="src/main/resources/address.jpg";
        String imageCaption="г. Астана, ул. Лесная, д. 3.";
            File image = ResourceUtils.getFile("classpath:" + imagePath);
            SendPhoto sendPhoto = new SendPhoto(chatId, image);
            sendPhoto.caption(imageCaption);
            telegramBot.execute(sendPhoto);

        }



    public void sendTimeTable(Long chatId) {
        var message = new SendMessage(chatId, "Часы работы: Пн-Пт 08:00 - 20:00");
        telegramBot.execute(message);
    }

    public void sendSafetyPrecautions(Long chatId) {
        sendMessage(chatId, SAFETY_PRECAUTIONS);
    }

    public void sendContact(Long chatId) {
        var message = new SendMessage(chatId, "Введите свой номер телефона ");
        telegramBot.execute(message);
    }
    public void answerContact(Long chatId) {
        var message = new SendMessage(chatId, "Ваш телефон принят. Скоро с Вами свяжется волонтер ");
        telegramBot.execute(message);
    }
    public void wrongContact(Long chatId) {
        var message = new SendMessage(chatId, "Неправильно введен номер телефона");
        telegramBot.execute(message);
    }



}
