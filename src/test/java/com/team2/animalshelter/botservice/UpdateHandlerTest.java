package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.team2.animalshelter.repository.UserRepository;
import com.team2.animalshelter.service.ShelterService;
import com.team2.animalshelter.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static com.team2.animalshelter.botservice.Command.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = UpdateHandler.class)
class UpdateHandlerTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private ShelterService shelterService;
    @SpyBean
    private KeyboardService keyboardService;
    @SpyBean
    private MessageService messageService;
    @SpyBean
    private CommandHandler commandHandler;
    @SpyBean
    private PhoneNumberHandler phoneNumberHandler;
    @MockBean
    private TelegramBot telegramBot;
    @InjectMocks
    @Autowired
    private UpdateHandler updateHandler;
    private static final long ID = 111111;

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
        SendMessage actual = getArgumentCaptor(Command.FAQ).getValue();
        assertAll(
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(ID),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo(InformationConstants.FAQ_COMMAND)
        );


    }

    static Stream<Arguments> paramByHandleCommandTest() {
        return Stream.of(
                Arguments.of(MAIN_MENU),
                Arguments.of(SHELTER_MENU),
                Arguments.of(CHOOSE_ANIMAL_TYPE)
        );
    }

    @ParameterizedTest
    @MethodSource("paramByHandleCommandTest")
    void handleCommand(Command command) throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(command).getValue();
        assertAll(
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(ID),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo("Выберите:"),
                () -> assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML"),
                () -> assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true)
        );
    }

    @Test
    void handleStartCommandIf() throws URISyntaxException, IOException {
        SendMessage actual = getArgumentCaptor(START).getValue();
        assertAll(
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(ID),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo("Привет"),
                () -> assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML"),
                () -> assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true)
        );
    }

    @Test
    void handleStartCommandElse() throws URISyntaxException, IOException {
        doReturn(true).when(userService).isRegistered(anyLong());
        SendMessage actual = getArgumentCaptor(START).getValue();
        assertAll(
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(ID),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo("Выберите:"),
                () -> assertThat(actual.getParameters().get("parse_mode")).isEqualTo("HTML"),
                () -> assertThat(actual.getParameters().get("disable_web_page_preview")).isEqualTo(true)
        );
    }

    @NotNull
    private ArgumentCaptor<SendMessage> getArgumentCaptor(Command start) throws IOException, URISyntaxException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(UpdateHandlerTest.class.getResource("update.json")).toURI()));
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