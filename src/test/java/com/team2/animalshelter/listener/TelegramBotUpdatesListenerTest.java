package com.team2.animalshelter.listener;

import com.pengrad.telegrambot.model.Update;
import com.team2.animalshelter.botservice.UpdateHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private Update update;

    @Mock
    private UpdateHandler updateHandler;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Test
    @DisplayName("process()")
    void shouldStartHandleUpdateMethodFromUpdateHandlerWhenUpdateIsComing() {
        telegramBotUpdatesListener.process(List.of(update));
        verify(updateHandler, times(1)).handleUpdate(update);
    }

}