package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.team2.animalshelter.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.team2.animalshelter.botservice.NavigationCommand.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = UpdateHandler.class)
class UpdateHandlerTest {

    @MockBean
    private UserService userService;
    @SpyBean
    private KeyboardService keyboardService;
    @SpyBean
    private MessageService messageService;
    @SpyBean
    private NavigationCommandHandler navigationCommandHandler;
    @MockBean
    private TelegramBot telegramBot;
    @InjectMocks
    @Autowired
    private UpdateHandler updateHandler;

    private static final String MESSAGE_TEXT = "some message";
    private static final int ONCE = 1;

    @BeforeEach
    public void beforeEach() {
        Mockito.when(telegramBot.execute(any())).thenReturn(
                BotUtils.fromJson(
                        """
                                {
                                  "ok": true
                                }
                                """,
                        SendResponse.class
                )
        );
    }

    @Test
    void handleFaqCommand() throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(NavigationCommand.FAQ).getValue();
        assertAll(
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo(InformationConstants.FAQ_COMMAND)
        );


    }

    @Test
    void handleStartCommandIf() throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(START).getValue();
        assertAll(
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo("Привет"),
                () -> assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML"),
                () -> assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true)
        );
    }

    @Test
    void handleStartCommandElse() throws URISyntaxException, IOException {
        doReturn(true).when(userService).isRegistered(anyLong());
        SendMessage actual = getArgumentCaptor(START).getValue();
        assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actual.getParameters().get("text")).isEqualTo("Выберите:");
        assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML");
        assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true);
    }

    @Test
    void handleMainMenuCommand() throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(MAIN_MENU).getValue();
        assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actual.getParameters().get("text")).isEqualTo("Выберите:");
        assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML");
        assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true);
    }

    @Test
    void handleShelterMenuCommand() throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(SHELTER_MENU).getValue();
        assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actual.getParameters().get("text")).isEqualTo("Выберите:");
        assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML");
        assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true);
    }

    @Test
    void handleAnimalMenuCommand() throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(CHOOSE_ANIMAL_TYPE).getValue();
        assertThat(actual.getParameters().get("chat_id")).isEqualTo(111L);
        assertThat(actual.getParameters().get("text")).isEqualTo("Выберите:");
        assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML");
        assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true);
    }

    @NotNull
    private ArgumentCaptor<SendMessage> getArgumentCaptor(NavigationCommand start) throws IOException, URISyntaxException {
        String json = Files.readString(
                Paths.get(UpdateHandlerTest.class.getResource("update.json").toURI()));
        Update update = getUpdate(json, start.getText());
        updateHandler.handleUpdate(update);
        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        return argumentCaptor;
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%text%", replaced), Update.class);
    }

}