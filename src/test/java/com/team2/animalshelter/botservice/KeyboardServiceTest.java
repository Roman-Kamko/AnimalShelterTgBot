package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KeyboardServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private KeyboardService keyboardService;

    @Test
    void sendGreetings() {
        keyboardService.sendGreetings(1L);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendMainMenu() {
        keyboardService.sendMainMenu(1L);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendChooseAnimalMenu() {
        keyboardService.sendChooseAnimalMenu(1L);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void sendShelterMenu() {
        keyboardService.sendShelterMenu(1L);
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

}