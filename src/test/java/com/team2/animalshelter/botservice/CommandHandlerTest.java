package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.service.ShelterService;
import com.team2.animalshelter.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.team2.animalshelter.botservice.Command.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CommandHandlerTest extends IntegrationTestBase {

    @Mock
    private Message message;

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

    @BeforeEach
    void beforeAll() {
        doReturn(chat).when(message).chat();
        doReturn(0L).when(chat).id();
    }

    @Test
    @DisplayName("showGreetingsIf")
    void shouldSendMainMenuIfUserIsRegister() {
        doReturn(true).when(userService).isRegistered(anyLong());
        commandHandler.handle(START.getText(), message);
        verify(keyboardService, times(ONCE)).sendMainMenu(anyLong());
    }

    @Test
    @DisplayName("showGreetingsElse")
    void shouldSendGreetingsIfUserIsNotRegister() {
        doReturn(false).when(userService).isRegistered(anyLong());
        commandHandler.handle(START.getText(), message);
        assertAll(
                () -> verify(userService, times(ONCE)).create(message.chat()),
                () -> verify(keyboardService, times(ONCE)).sendGreetings(anyLong())
        );
    }

    @Test
    @DisplayName("showShelterMenu")
    void shouldSendShelterMenu() {
        commandHandler.handle(SHELTER_MENU.getText(), message);
        verify(keyboardService, times(ONCE)).sendShelterMenu(anyLong());
    }

    @Test
    @DisplayName("showChooseAnimalMenu")
    void shouldSendChooseAnimalMenu() {
        commandHandler.handle(CHOOSE_ANIMAL_TYPE.getText(), message);
        verify(keyboardService, times(ONCE)).sendChooseAnimalMenu(anyLong());
    }

    @Test
    @DisplayName("showFaqMenu")
    void shouldSendFaqMessage() {
        commandHandler.handle(FAQ.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), anyString());
    }

    @Test
    @DisplayName("showShelterContact")
    void shouldSendShelterContact() {
        commandHandler.handle(SHELTER_CONTACT.getText(), message);
        verify(messageService, times(ONCE))
                .sendMessage(
                        anyLong(),
                        eq("Телефон охраны для оформления пропуска: +7-888-88-88; Общий телефон: +7-999-99-99")
                );
    }

    @Test
    @DisplayName("showShelterAddress")
    void shouldSendShelterAddress() {
        commandHandler.handle(SHELTER_ADDRESS.getText(), message);
        assertAll(
                () -> verify(messageService, times(ONCE)).sendMessage(anyLong(), eq("г. Астана, ул. Лесная, д. 3.")),
                () -> verify(messageService, times(ONCE)).sendPhoto(anyLong(), eq("image/shelters/address.jpg"))
        );


    }

    @Test
    @DisplayName("showTimeTable")
    void shouldSendTimeTable() {
        commandHandler.handle(TIME_TABLE.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq("Часы работы: Пн-Пт 08:00 - 20:00"));
    }

    @Test
    @DisplayName("showSafetyPrecautions")
    void shouldSendSafetyPrecautions() {
        commandHandler.handle(SAFETY_PRECAUTIONS.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.SAFETY_PRECAUTIONS));
    }

    @Test
    @DisplayName("sendContactRequest")
    void shouldSendContactRequest() {
        commandHandler.handle(SEND_CONTACT.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.CONTACT_REQUEST));
    }

    @Test
    @DisplayName("sendRules")
    void shouldSendShelterRulesInfo() {
        commandHandler.handle(RULES.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.DATING_RULES));
    }

    @Test
    @DisplayName("sendDocList")
    void shouldSendDocumentationListInfo() {
        commandHandler.handle(DOC_LIST.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.DOC_LIST));
    }

    @Test
    @DisplayName("sendDenialReasons")
    void shouldSendDenialReasonsInfo() {
        commandHandler.handle(DENIAL_REASONS.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.DENIAL_REASONS));
    }

    @Test
    @DisplayName("sendCatTransportation")
    void shouldSendCatTransportationInfo() {
        commandHandler.handle(CAT_TRANSPORTATION.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.CAT_TRANSPORTATION));
    }

    @Test
    @DisplayName("sendHomeForKitty")
    void shouldSendHomeForKittyInfo() {
        commandHandler.handle(HOME_FOR_KITTY.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.HOME_FOR_KITTY));
    }

    @Test
    @DisplayName("sendHomeForAdultCat")
    void shouldSendHomeForAdultCatInfo() {
        commandHandler.handle(HOME_FOR_ADULT_CAT.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.HOME_FOR_ADULT_CAT));
    }

    @Test
    @DisplayName("sendHomeForDisCat")
    void shouldSendHomeForDistinctCatInfo() {
        commandHandler.handle(HOME_FOR_DIS_CAT.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.HOME_FOR_DISABLED_CAT));
    }

    @Test
    @DisplayName("sendDogTransportation")
    void shouldSendDogTransportationInfo() {
        commandHandler.handle(DOG_TRANSPORTATION.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.DOG_TRANSPORTATION));
    }

    @Test
    @DisplayName("sendHomeForPuppy")
    void shouldSendHomeForPuppyInfo() {
        commandHandler.handle(HOME_FOR_PUPPY.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.HOME_FOR_PUPPY));
    }

    @Test
    @DisplayName("sendHomeForAdultDog")
    void shouldSendHomeForAdultDogInfo() {
        commandHandler.handle(HOME_FOR_ADULT_DOG.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.HOME_FOR_ADULT_DOG));
    }

    @Test
    @DisplayName("sendHomeForDisDog")
    void shouldSendHomeForDisDogInfo() {
        commandHandler.handle(HOME_FOR_DIS_DOG.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.HOME_FOR_DISABLED_DOG));
    }

    @Test
    @DisplayName("sendCynologistAdvice")
    void shouldSendCynologistAdviceInfo() {
        commandHandler.handle(CYNOLOGIST_ADVISE.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.CYNOLOGIST_ADVICE));
    }

    @Test
    @DisplayName("sendProvenCynologists")
    void shouldSendProvenCynologistsInfo() {
        commandHandler.handle(PROVEN_CYNOLOGISTS.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.PROVEN_CYNOLOGISTS));
    }

    @Test
    @DisplayName("sendCatList")
    void shouldSendCatList() {
        commandHandler.handle(CAT_SHELTER.getText(), message);
        var expected1 = """
                Животное: Кошка,
                Кличка: Марсель,
                Возраст: 4,
                Порода: Корниш-рекс
                """;
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(expected1));
        var expected2 = """
                Животное: Кошка,
                Кличка: Бонифаций,
                Возраст: 9,
                Порода: Шотландская-веслоухая
                """;
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(expected2));
        var expected3 = """
                Животное: Кошка,
                Кличка: Пушок,
                Возраст: 3,
                Порода: Сфинкс
                """;
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(expected3));
    }

    @Test
    @DisplayName("sendDogList")
    void shouldSendDogList() {
        commandHandler.handle(DOG_SHELTER.getText(), message);
        var expected1 = """
                Животное: Собака,
                Кличка: Граф,
                Возраст: 10,
                Порода: Лайка
                """;
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(expected1));
        var expected2 = """
                Животное: Собака,
                Кличка: Герда,
                Возраст: 4,
                Порода: Доберман
                """;
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(expected2));
    }

    @Test
    void callVolunteer() {
        commandHandler.handle(CALL_A_VOLUNTEER.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.CALL_VOLUNTEER));
        verify(messageService, times(ONCE)).sendForwardMessageToVolunteers(any(Message.class));
    }

    @Test
    void sendReportInfo() {
        commandHandler.handle(SEND_REPORT_FORM.getText(), message);
        verify(messageService, times(ONCE)).sendMessage(anyLong(), eq(InformationConstants.REPORT_MESSAGE));
    }

}