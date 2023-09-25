package com.team2.animalshelter.botservice;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.entity.Volunteer;
import com.team2.animalshelter.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.Optional;


/**
 * Класс для отправки сообщений пользователю
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;

    /**
     * Отправка сообщения конкретному пользователю телеграм бота.
     *
     * @param chatId чат в который нужно отправить сообщение.
     * @param text текст сообщения.
     */
    public void sendMessage(Long chatId, String text) {
        var message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    /**
     * Отправка фото конкретному пользователю телеграм бота.
     *
     * @param chatId чат в который нужно отправить фото.
     * @param path путь к фото.
     */
    @SneakyThrows
    public void sendPhoto(Long chatId, String path) {
        var image = ResourceUtils.getFile(path);
        var sendPhoto = new SendPhoto(chatId, image);
        telegramBot.execute(sendPhoto);
    }

    /**
     * Отправка сообщения всем волонтерам, с указанием идентификатора опекуна.
     *
     * @param text текст сообщения.
     * @param ownerId идентификатора опекуна.
     */
    public void sendMessageToVolunteers(String text, Long ownerId) {
        var message = ownerId + " " + text;
        var volunteers = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteers) {
            sendMessage(volunteer.getTelegramId(), message);
        }
    }


    /**
     * Отправка сообщения всем волонтерам.
     *
     * @param text текст сообщения.
     */
    public void sendMessageToVolunteers(String text) {
        var volunteers = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteers) {
            sendMessage(volunteer.getTelegramId(), text);
        }
    }

    /**
     * Пересылка сообщения волонтерам, которое пользователь отправил боту.
     *
     * @param message сообщение которое нужно переслать волонтерам.
     */
    public void sendForwardMessageToVolunteers(Message message) {
        var volunteers = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteers) {
            var forwardMessage = new ForwardMessage(volunteer.getTelegramId(), message.chat().id(), message.messageId());
            telegramBot.execute(forwardMessage);
        }
    }

    /**
     * Получение фото отправленного пользователем.
     *
     * @param report отчет из которого получим телеграмовский айдишник фотографии.
     * @return {@code Optional.of(byte[])} если {@link Report#getPhoto()} содержит телеграмовский айдишник фотографии,
     * иначе {@code Optional.empty()}
     */
    @SneakyThrows
    public Optional<byte[]> getPhoto(Report report) {
        var response = telegramBot.execute(new GetFile(report.getPhoto()));
        var content = telegramBot.getFileContent(response.file());
        return Optional.ofNullable(content);
    }

}




