package com.team2.animalshelter.botservice;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.team2.animalshelter.entity.Volunteer;
import com.team2.animalshelter.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;


/**
 * Класс для отправки сообщений пользователю
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final TelegramBot telegramBot;
    private final VolunteerRepository volunteerRepository;

    public void sendMessage(Long chatId, String text) {
        var message = new SendMessage(chatId, text);
        telegramBot.execute(message);
    }

    @SneakyThrows
    public void sendPhoto(Long chatId, String path) {
        var image = ResourceUtils.getFile(path);
        var sendPhoto = new SendPhoto(chatId, image);
        telegramBot.execute(sendPhoto);
    }

    public void sendMessageToVolunteers(String text, Long id) {
        var message = id + " " + text;
        var volunteers = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteers) {
            sendMessage(volunteer.getTelegramId(), message);
        }
    }

    public void sendForwardMessageToVolunteers(Message message) {
        var volunteers = volunteerRepository.findAll();
        for (Volunteer volunteer : volunteers) {
            var forwardMessage = new ForwardMessage(volunteer.getTelegramId(), message.chat().id(), message.messageId());
            telegramBot.execute(forwardMessage);
        }
    }

}




