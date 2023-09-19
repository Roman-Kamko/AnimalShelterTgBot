package com.team2.animalshelter.service;

import com.team2.animalshelter.IntegrationTestBase;
import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.dto.in.ReportDtoIn;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.entity.enums.AnimalType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.testcontainers.shaded.com.google.common.net.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ReportServiceTest extends IntegrationTestBase {

    @Autowired
    private ReportService reportService;
    private static final long ID = 1L;
    private static AdaptationDtoOut adaptation;
    private static MockMultipartFile partTwo;

    @BeforeAll
    static void beforeAll() throws IOException {
        adaptation = new AdaptationDtoOut(
                1L,
                LocalDate.of(2023, 9, 9),
                LocalDate.of(2023, 10, 9),
                null,
                false,
                AdaptationStatus.IN_PROGRESS,
                new AnimalDtoOut(
                        3L,
                        "Барсик",
                        5,
                        "Сибирская",
                        true,
                        AnimalType.CAT
                ),
                new OwnerDto(
                        111111L,
                        "Ivan_Ivanov",
                        "Иван",
                        "Иванов",
                        "+79115648532"
                )
        );
        partTwo = new MockMultipartFile(
                "photo",
                "testReport.jpg",
                MediaType.ANY_IMAGE_TYPE.type(),
                Files.readAllBytes(Path.of("testReport.jpg"))
        );
    }

    @Test
    void shouldReturnReportById() {
        var actualResult = reportService.findById(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new ReportDtoOut(
                1L,
                "Забился в угол, не выходит есть и пить",
                "http://localhost:8081/api/v1/reports/1/photo",
                LocalDate.of(2023, 9, 9),
                adaptation
        );
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

    @Test
    void shouldReturnAllReport() {
        var reports = reportService.findAll();
        assertThat(reports).isNotEmpty().hasSize(4);
    }

    @Test
    void shouldCreateReport() throws IOException {
        var partOne = new ReportDtoIn(
                "Стал более активным",
                ID
        );
        var actual = reportService.create(partOne, partTwo);
        var expected = new ReportDtoOut(
                5L,
                "Стал более активным",
                "http://localhost:8081/api/v1/reports/5/photo",
                LocalDate.now(),
                adaptation
        );
        var reports = reportService.findAll();
        assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(reports).contains(actual)
        );
    }

    @Test
    void shouldUpdateReport() throws IOException {
        var partOne = new ReportDtoIn(
                "Забился в угол и шипит, не выходит есть и пить",
                ID
        );
        var actualResult = reportService.update(ID, partOne, partTwo);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = new ReportDtoOut(
                ID,
                "Забился в угол и шипит, не выходит есть и пить",
                "http://localhost:8081/api/v1/reports/1/photo",
                LocalDate.now(),
                adaptation
        );
        var reports = reportService.findAll();
        actualResult.ifPresent(actual -> assertAll(
                () -> assertThat(actual).isEqualTo(expected),
                () -> assertThat(reports).contains(actual)
        ));
    }

    @Test
    void shouldReturnTrueWhenDeleteIfReportExist() {
        var delete = reportService.delete(ID);
        assertThat(delete).isTrue();

        var report = reportService.findById(ID);
        assertThat(report.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDeleteIfReportNotExist() {
        var delete = reportService.delete(0L);
        assertThat(delete).isFalse();
    }

    @Test
    void shouldReturnPhotoWhenGetImage() throws IOException {
        var actualResult = reportService.getImage(ID);
        assertThat(actualResult.isPresent()).isTrue();

        var expected = Files.readAllBytes(Path.of("image", "reports", "1-20230909.jpg"));
        actualResult.ifPresent(actual -> assertThat(actual).isEqualTo(expected));
    }

}