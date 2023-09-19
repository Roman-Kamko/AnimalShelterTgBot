package com.team2.animalshelter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Почти весь класс {@link ImageService} косвенно протестирован в интеграционных тестах
 * {@link ShelterServiceTest} и {@link ReportServiceTest}.
 */
@SpringBootTest(classes = ImageService.class)
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    void shouldReturnEmptyOptionalIfFileNotExist() {
        var actual = imageService.getImage("test.jpg", "reports");
        assertThat(actual.isEmpty()).isTrue();
    }

}