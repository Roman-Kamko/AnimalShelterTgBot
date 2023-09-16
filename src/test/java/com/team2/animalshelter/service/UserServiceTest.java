package com.team2.animalshelter.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.entity.Shelter;
import com.team2.animalshelter.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest extends IntegrationTestBase {

    @Autowired
    private UserService userService;
    private static final long ID = 111111L;
    private UserDto userDto;
    private User user;
    private Shelter shelter;

    @BeforeEach
    void beforeEach() {
        userDto = new UserDto(
                111111L,
                "Ivan_Ivanov",
                "Ivan",
                "Ivanov",
                "+79115648532"
        );
        shelter = new Shelter();
        shelter.setId(1L);
        shelter.setName("testName");
        user = new User();
        user.setTelegramId(111111L);
        user.setUsername("Ivan_Ivanov");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhoneNumber("+79115648532");
        user.setShelter(shelter);
    }

    @Test
    void shouldReturnOptionalOfUserDtoWhenFindById() {
        userService.findById(ID).ifPresent(actual ->
                assertThat(actual).isEqualTo(userDto)
        );
    }

    @Test
    void shouldReturnAllUsersDtoWhenFindAll() {
        var users = userService.findAll();
        assertThat(users).hasSize(7);
    }

    @Test
    void shouldReturnTrueIfUserIsRegistered() {
        var registered = userService.isRegistered(ID);
        assertThat(registered).isTrue();
    }

    @Test
    void shouldReturnFalseIfUserIsNotRegistered() {
        var registered = userService.isRegistered(1L);
        assertThat(registered).isFalse();
    }

    @Test
    void shouldCreateUserFromChat() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(Objects.requireNonNull(UserServiceTest.class.getResource("chat.json")).toURI()));
        var chat = BotUtils.fromJson(json, Chat.class);
        var userFromChat = new UserDto(
                111111L,
                "Ivan_Ivanov",
                "Ivan",
                "Ivanov",
                null
        );
        assertThat(userService.create(chat)).isEqualTo(userFromChat);
    }

    @Test
    void shouldCreateUserFromUserDto() {
        assertThat(userService.create(userDto)).isEqualTo(userDto);
    }

    @Test
    void shouldUpdateUser() {
        var updatedUser = new UserDto(
                111111L,
                "Ivan_Ivanov123",
                "Ivan123",
                "Ivanov123",
                "+71111111111"
        );
        userService.update(updatedUser).ifPresent(actual ->
                assertThat(actual).isEqualTo(updatedUser)
        );
    }

    @Test
    void shouldReturnTrueWhenDeleteIfUserPresent() {
        assertThat(userService.delete(ID)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeleteIfUserNotPresent() {
        assertThat(userService.delete(1L)).isFalse();
    }

}