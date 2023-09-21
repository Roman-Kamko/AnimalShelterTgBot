package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PhoneNumberHandlerTest extends IntegrationTestBase {

    @Mock
    private Chat chat;

    @SpyBean
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private PhoneNumberHandler phoneNumberHandler;

    @Test
    @DisplayName("handlePositive")
    void shouldReturnAnswerMessageIfPhoneNumberCorrectAndSaveNewPhoneNumber() {
        doReturn(111111L).when(chat).id();
        phoneNumberHandler.handle("+71112223344", chat);
        verify(messageService, times(1)).sendMessage(anyLong(), eq(InformationConstants.PHONE_ACCEPTED));
        userRepository.findById(chat.id())
                .ifPresent(user -> assertThat(user.getPhoneNumber()).isEqualTo("+71112223344"));
    }

    @Test
    @DisplayName("handleNegative")
    void shouldReturnWrongContactMessageIfPhoneNumberNotCorrectAndDontSavePhoneNumber() {
        doReturn(111111L).when(chat).id();
        phoneNumberHandler.handle("+7111222334455", chat);
        verify(messageService, times(1)).sendMessage(anyLong(), eq(InformationConstants.PHONE_WRONG));
        userRepository.findById(chat.id())
                .ifPresent(user -> assertThat(user.getPhoneNumber()).isEqualTo("+79115648532"));
    }

}