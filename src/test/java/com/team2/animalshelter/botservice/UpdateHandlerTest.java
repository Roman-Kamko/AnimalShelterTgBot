package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateHandlerTest {

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private NavigationCommandHandler navigationCommandHandler;

    @InjectMocks
    private UpdateHandler updateHandler;

    private static final String MESSAGE_TEXT = "some message";
    private static final int ONCE = 1;

    @Test
    @DisplayName("handleUpdate")
    void shouldStartHandleMethodIfMessageTextNotNull() {
        doReturn(message).when(update).message();
        doReturn(MESSAGE_TEXT).when(message).text();
        doReturn(new Chat()).when(message).chat();
        updateHandler.handleUpdate(update);
        verify(navigationCommandHandler, times(ONCE)).handle(message.text(), message.chat());
    }

}