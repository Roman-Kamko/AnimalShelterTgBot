package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateHandler {

    //    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;

    public void handleUpdate(Update update) {

    }

}
