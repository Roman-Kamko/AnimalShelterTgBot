package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team2.animalshelter.constant.ButtonKey.*;

@Service
@RequiredArgsConstructor
public class UpdateHandler {

    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;

    public void handleUpdate(Update update) {

    }

}
