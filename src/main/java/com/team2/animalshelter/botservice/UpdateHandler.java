package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.team2.animalshelter.entity.User;
import com.team2.animalshelter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для обработки Update'ов
 */
@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final NavigationCommandHandler navigationCommandHandler;
    private final MessageService messageService;
    private final UserRepository userRepository;

    public void handleUpdate(Update update) {
        if (update.message() != null) {
            Message message = update.message();
            Chat chat = message.chat();
            String text = message.text();

            if (text != null) {
                navigationCommandHandler.handle(text, chat);

                //if (update.message() != null && update.message().text() != null) {

                Pattern pattern = Pattern.compile("^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$");

                Matcher matcher = pattern.matcher(Objects.requireNonNull(text));
                if (matcher.matches()) {
                    String telephone = matcher.group();
                    User user = new User();
                    user.setTelegramId(update.message().chat().id());
                    user.setFirstname(update.message().from().firstName());
                    user.setPhoneNumber(telephone);
                    userRepository.save(user);
                    messageService.answerContact(update.message().chat().id());}
                } else {
                    messageService.wrongContact(update.message().chat().id());
                }
            }
        }
    }







