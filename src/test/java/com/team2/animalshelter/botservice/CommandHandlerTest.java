package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.service.ShelterService;
import com.team2.animalshelter.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.team2.animalshelter.botservice.Command.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = CommandHandler.class)
class CommandHandlerTest extends IntegrationTestBase {

    @Mock
    private Chat chat;

    @MockBean
    private UserService userService;

    @MockBean
    private KeyboardService keyboardService;

    @MockBean
    private MessageService messageService;

    @Autowired
    private ShelterService shelterService;

    @InjectMocks
    @Autowired
    private CommandHandler commandHandler;

    private static final int ONCE = 1;

    @Test
    @DisplayName("showGreetingsIf")
    void shouldSendMainMenuIfUserIsRegister() {
        doReturn(true).when(userService).isRegistered(anyLong());
        commandHandler.handle(START.getText(), chat);
        verify(keyboardService, times(ONCE)).sendMainMenu(anyLong());
    }

    @Test
    @DisplayName("showGreetingsElse")
    void shouldSendGreetingsIfUserIsNotRegister() {
        doReturn(false).when(userService).isRegistered(anyLong());
        commandHandler.handle(START.getText(), chat);
        assertAll(
                () -> verify(userService, times(ONCE)).create(chat),
                () -> verify(keyboardService, times(ONCE)).sendGreetings(anyLong())
        );
    }

    @Test
    @DisplayName("showShelterMenu")
    void shouldSendShelterMenu() {
        commandHandler.handle(SHELTER_MENU.getText(), chat);
        verify(keyboardService, times(ONCE)).sendShelterMenu(anyLong());
    }

    @Test
    @DisplayName("showChooseAnimalMenu")
    void shouldSendChooseAnimalMenu() {
        commandHandler.handle(CHOOSE_ANIMAL_TYPE.getText(), chat);
        verify(keyboardService, times(ONCE)).sendChooseAnimalMenu(anyLong());
    }

    @Test
    @DisplayName("showFaqMenu")
    void shouldSendFaqMessage() {
        commandHandler.handle(FAQ.getText(), chat);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), anyString());
    }

    @Test
    @DisplayName("showShelterContact")
    void shouldSendShelterContact() {
        commandHandler.handle(SHELTER_CONTACT.getText(), chat);
        verify(messageService, times(ONCE))
                .sendMessage(
                        anyLong(),
                        eq("Телефон охраны для оформления пропуска: +7-888-88-88; Общий телефон: +7-999-99-99")
                );
    }

    @Test
    @DisplayName("showShelterAddress")
    void shouldSendShelterAddress() {
        commandHandler.handle(SHELTER_ADDRESS.getText(), chat);
        assertAll(
                () -> verify(messageService, times(ONCE)).sendMessage(anyLong(), eq("г. Астана, ул. Лесная, д. 3.")),
                () -> verify(messageService, times(ONCE)).sendPhoto(anyLong(), eq("image/shelters/address.jpg"))
        );


    }

    @Test
    @DisplayName("showTimeTable")
    void shouldSendTimeTable() {
        commandHandler.handle(TIME_TABLE.getText(), chat);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq("Часы работы: Пн-Пт 08:00 - 20:00"));
    }

}