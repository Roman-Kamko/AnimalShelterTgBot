package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Message;
import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.entity.Owner;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.repository.OwnerRepository;
import com.team2.animalshelter.service.AdaptationService;
import com.team2.animalshelter.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportHandlerTest {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private ReportService reportService;
    @Mock
    private AdaptationService adaptationService;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private ReportHandler reportHandler;

    private static Owner owner;
    private static final int ONCE = 1;
    private static final long ID = 111111L;

    @BeforeAll
    static void beforeAll() {
        owner = new Owner();
        owner.setTelegramId(ID);
    }

    @Test
    void handlePositive() throws URISyntaxException, IOException {
        doReturn(List.of(owner)).when(ownerRepository).findAll();
        doReturn(LocalDate.of(2023, 9, 20))
                .when(adaptationService).findLastReportDate(ID);
        var json = Files.readString(Paths.get(Objects.requireNonNull(
                ReportHandlerTest.class.getResource("messageWithCaption.json")).toURI())
        );
        var message = BotUtils.fromJson(json, Message.class);
        var createdReport = new ReportDtoOut(
                5L,
                "test description",
                "http://localhost:8081/api/v1/reports/5/photo",
                LocalDate.now(),
                new AdaptationDtoOut(
                        1L,
                        LocalDate.of(2023, 9, 9),
                        LocalDate.of(2023, 10, 9),
                        null,
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
                                ID,
                                "Ivan_Ivanov",
                                "Иван",
                                "Иванов",
                                "+79115648532"
                        )
                )
        );
        doReturn(createdReport).when(reportService).createFromTelegram(message);
        reportHandler.handle(message);

        verify(messageService, times(ONCE))
                .sendMessage(eq(ID), eq(InformationConstants.REPORT_CREATED));
        verify(messageService, times(ONCE))
                .sendForwardMessageToVolunteers(message);
        verify(messageService, times(ONCE))
                .sendMessageToVolunteers(eq("id отчета: " + createdReport.getId()));
    }

    @Test
    void handleWhenReportAlreadyCreatedToDay() throws URISyntaxException, IOException {
        doReturn(List.of(owner)).when(ownerRepository).findAll();
        doReturn(LocalDate.now()).when(adaptationService).findLastReportDate(ID);
        startHandle("messageWithCaption.json");
        verify(messageService, times(ONCE))
                .sendMessage(eq(ID), eq(InformationConstants.REPORT_EXIST));
    }

    @Test
    void handleWhenCaptionNotExistInMessage() throws URISyntaxException, IOException {
        doReturn(List.of(owner)).when(ownerRepository).findAll();
        startHandle("message.json");
        verify(messageService, times(ONCE)).sendMessage(eq(ID), eq(InformationConstants.NO_CAPTION));
    }

    @Test
    void handleWhenReportSendNotOwner() throws URISyntaxException, IOException {
        doReturn(Collections.emptyList()).when(ownerRepository).findAll();
        startHandle("message.json");
        verify(messageService, times(ONCE)).sendMessage(eq(ID), eq(InformationConstants.NOT_OWNER));
    }

    private void startHandle(String jsonPath) throws URISyntaxException, IOException {
        var json = Files.readString(Paths.get(Objects.requireNonNull(
                ReportHandlerTest.class.getResource(jsonPath)).toURI())
        );
        var message = BotUtils.fromJson(json, Message.class);
        reportHandler.handle(message);
    }

}