package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.team2.animalshelter.botservice.InformationConstants.*;
import static com.team2.animalshelter.botservice.InformationConstants.PHONE_ACCEPTED;

@Component
@RequiredArgsConstructor
public class PhoneNumberHandler {

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final Pattern pattern = Pattern.compile("\\+7[0-9]{10}$");

    public void handle(String text, Chat chat) {
        var matcher = pattern.matcher(Objects.requireNonNull(text));
        if (matcher.matches()) {
            String phone = matcher.group();
            userRepository.findById(chat.id())
                    .ifPresent(user -> {
                        user.setPhoneNumber(phone);
                        userRepository.saveAndFlush(user);
                    });
            answerContact(chat.id());
        } else {
            wrongContact(chat.id());
        }
    }

    private void answerContact(Long chatId) {
        messageService.sendMessage(chatId, PHONE_ACCEPTED);
    }

    private void wrongContact(Long chatId) {
        messageService.sendMessage(chatId, PHONE_WRONG);
    }

}
