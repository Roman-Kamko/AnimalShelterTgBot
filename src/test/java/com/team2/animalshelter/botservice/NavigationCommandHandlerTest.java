package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.team2.animalshelter.botservice.NavigationCommand.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = NavigationCommandHandler.class)
class NavigationCommandHandlerTest {

    @Mock
    private Chat chat;

    @MockBean
    private UserService userService;

    @MockBean
    private KeyboardService keyboardService;

    @MockBean
    private MessageService messageService;

    @InjectMocks
    @Autowired
    private NavigationCommandHandler navigationCommandHandler;

    private static final int ONCE = 1;

    @Test
    @DisplayName("showGreetingsIf")
    void shouldSendMainMenuIfUserIsRegister() {
        doReturn(true).when(userService).isRegistered(anyLong());
        navigationCommandHandler.handle(START.getText(), chat);
        verify(keyboardService, times(ONCE)).sendMainMenu(anyLong());
    }

    @Test
    @DisplayName("showGreetingsElse")
    void shouldSendGreetingsIfUserIsNotRegister() {
        doReturn(false).when(userService).isRegistered(anyLong());
        navigationCommandHandler.handle(START.getText(), chat);
        assertAll(
                () -> verify(userService, times(ONCE)).create(chat),
                () -> verify(keyboardService, times(ONCE)).sendGreetings(anyLong())
        );
    }

    @Test
    @DisplayName("showShelterMenu")
    void shouldSendShelterMenu() {
        navigationCommandHandler.handle(SHELTER_MENU.getText(), chat);
        verify(keyboardService, times(ONCE)).sendShelterMenu(anyLong());
    }

    @Test
    @DisplayName("showChooseAnimalMenu")
    void shouldSendChooseAnimalMenu() {
        navigationCommandHandler.handle(CHOOSE_ANIMAL_TYPE.getText(), chat);
        verify(keyboardService, times(ONCE)).sendChooseAnimalMenu(anyLong());
    }

    @Test
    @DisplayName("showFaqMenu")
    void shouldSendFaqMessage() {
        navigationCommandHandler.handle(FAQ.getText(), chat);
        verify(messageService, times(ONCE)).sendFaqMessage(anyLong());
    }

}