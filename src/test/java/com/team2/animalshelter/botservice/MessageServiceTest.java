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
class MessageServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private MessageService messageService;

    @Test
    void sendFaqMessage() {
        messageService.sendMessage(1L, "text");
        verify(telegramBot, times(1)).execute(any(SendMessage.class));
    }

}