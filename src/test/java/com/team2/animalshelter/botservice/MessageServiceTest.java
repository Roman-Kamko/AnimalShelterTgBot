package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.ForwardMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.team2.animalshelter.entity.Volunteer;
import com.team2.animalshelter.repository.VolunteerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private VolunteerRepository volunteerRepository;
    @InjectMocks
    private MessageService messageService;
    private static final String TEXT = "text";
    private static final long USER_ID = 111111L;
    private static final long VOL_ID = 123123L;
    private static final int ONCE = 1;

    @Test
    void sendMessage() {
        messageService.sendMessage(USER_ID, TEXT);
        verify(telegramBot, times(ONCE)).execute(any(SendMessage.class));
    }

    @Test
    void sendPhoto() {
        messageService.sendPhoto(USER_ID, TEXT);
        verify(telegramBot, times(ONCE)).execute(any(SendPhoto.class));
    }

    @Test
    void sendMessageToVolunteersWithId() {
        var volunteer = new Volunteer();
        volunteer.setTelegramId(VOL_ID);
        var volunteers = new ArrayList<>(List.of(volunteer));
        doReturn(volunteers).when(volunteerRepository).findAll();
        messageService.sendMessageToVolunteers(TEXT, USER_ID);
        var actual = getMessage(SendMessage.class);
        assertAll(
                () -> verify(telegramBot, times(ONCE)).execute(any(SendMessage.class)),
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(VOL_ID),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo(USER_ID + " " + TEXT)
        );
    }

    @Test
    void sendMessageToVolunteers() {
        var volunteer = new Volunteer();
        volunteer.setTelegramId(VOL_ID);
        var volunteers = new ArrayList<>(List.of(volunteer));
        doReturn(volunteers).when(volunteerRepository).findAll();
        messageService.sendMessageToVolunteers(TEXT);
        var actual = getMessage(SendMessage.class);
        assertAll(
                () -> verify(telegramBot, times(ONCE)).execute(any(SendMessage.class)),
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(VOL_ID),
                () -> assertThat(actual.getParameters().get("text")).isEqualTo(TEXT)
        );
    }

    @Test
    void sendForwardMessageToVolunteers() throws URISyntaxException, IOException {
        var volunteer = new Volunteer();
        volunteer.setTelegramId(VOL_ID);
        var volunteers = new ArrayList<>(List.of(volunteer));
        doReturn(volunteers).when(volunteerRepository).findAll();
        var json = Files.readString(Paths.get(MessageServiceTest.class.getResource("message.json").toURI()));
        var message = BotUtils.fromJson(json, Message.class);
        messageService.sendForwardMessageToVolunteers(message);
        var actual = getMessage(ForwardMessage.class);
        assertAll(
                () -> verify(telegramBot, times(ONCE)).execute(any(ForwardMessage.class)),
                () -> assertThat(actual.getParameters().get("chat_id")).isEqualTo(VOL_ID),
                () -> assertThat(actual.getParameters().get("from_chat_id")).isEqualTo(USER_ID),
                () -> assertThat(actual.getParameters().get("message_id")).isEqualTo(ONCE)
        );
    }

    private <T extends BaseRequest> T getMessage(Class<T> clazz) {
        var argumentCaptor = ArgumentCaptor.forClass(clazz);
        verify(telegramBot).execute(argumentCaptor.capture());
        return argumentCaptor.getValue();
    }

}