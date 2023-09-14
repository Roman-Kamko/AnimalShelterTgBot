package com.team2.animalshelter.controller;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerIT extends IntegrationTestBase {

    @Autowired
    private UserService userService;

    @Test
    void findById() {
        var maybeUser = userService.findById(111111L);
        maybeUser.ifPresent(user -> {
            assertAll(
                    () -> assertThat(user.getTelegramId()).isEqualTo(111111L),
                    () -> assertThat(user.getFirstname()).isEqualTo("Ivan"),
                    () -> assertThat(user.getLastname()).isEqualTo("Ivanov")
            );
        });
    }

}