package com.team2.animalshelter.scheduler;

import com.team2.animalshelter.botservice.MessageService;
import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.entity.Owner;
import com.team2.animalshelter.entity.enums.AdaptationStatus;
import com.team2.animalshelter.repository.AdaptationRepository;
import com.team2.animalshelter.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.team2.animalshelter.botservice.InformationConstants.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BotSchedulerTest {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private AdaptationRepository adaptationRepository;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private BotScheduler botScheduler;
    private static Owner owner;
    private static Adaptation adaptation;
    private static final long ID = 111111L;

    @BeforeAll
    static void beforeAll() {
        owner = new Owner();
        owner.setTelegramId(ID);
        adaptation = new Adaptation();
        adaptation.setOwner(owner);
    }

    @Test
    void successfulAdaptation() {
        doReturn(List.of(adaptation)).when(adaptationRepository).findAllWhereEndDateEqualsLastReportDate();
        botScheduler.successfulAdaptation();
        verify(messageService).sendMessageToVolunteers(eq(END_ADAPTATION), eq(ID));
    }

    @Test
    void sendAdaptationWarning() {
        doReturn(List.of(owner)).when(ownerRepository).findAll();
        doReturn(true).when(adaptationRepository).isExpiredAdaptation(ID);
        botScheduler.sendAdaptationWarning();
        assertAll(
                () -> verify(messageService).sendMessage(eq(111111L), eq(ADAPTATION_WARN)),
                () -> verify(messageService).sendMessageToVolunteers(eq(VOL_ADAPTATION_WARN), eq(ID))

        );
    }

    @Test
    void sendNotificationAboutAdaptationStatusWithNOT_SUCCESSFUL() {
        adaptation.setAdaptationStatus(AdaptationStatus.NOT_SUCCESSFUL);
        doReturn(List.of(adaptation)).when(adaptationRepository)
                .findAllByAdaptationStatus(AdaptationStatus.NOT_SUCCESSFUL, AdaptationStatus.EXTENDED, AdaptationStatus.SUCCESSFUL);
        botScheduler.sendNotificationAboutAdaptationStatus();
        assertAll(
                () -> verify(messageService).sendMessage(eq(ID), eq(STATUS_NOT_SUCCESSFUL)),
                () -> verify(messageService).sendMessageToVolunteers(eq(VOL_NOTIFICATION_NOT_SUCCESSFUL), eq(ID))
        );
    }

    @Test
    void sendNotificationAboutAdaptationStatusWithEXTENDED() {
        adaptation.setAdaptationStatus(AdaptationStatus.EXTENDED);
        doReturn(List.of(adaptation)).when(adaptationRepository)
                .findAllByAdaptationStatus(AdaptationStatus.NOT_SUCCESSFUL, AdaptationStatus.EXTENDED, AdaptationStatus.SUCCESSFUL);
        botScheduler.sendNotificationAboutAdaptationStatus();
        assertAll(
                () -> verify(messageService).sendMessage(eq(ID), eq(STATUS_EXTENDED)),
                () -> verify(messageService).sendMessageToVolunteers(eq(VOL_NOTIFICATION_EXTENDED), eq(ID))
        );
    }

    @Test
    void sendNotificationAboutAdaptationStatusWithSUCCESSFUL() {
        adaptation.setAdaptationStatus(AdaptationStatus.SUCCESSFUL);
        doReturn(List.of(adaptation)).when(adaptationRepository)
                .findAllByAdaptationStatus(AdaptationStatus.NOT_SUCCESSFUL, AdaptationStatus.EXTENDED, AdaptationStatus.SUCCESSFUL);
        botScheduler.sendNotificationAboutAdaptationStatus();
        assertAll(
                () -> verify(messageService).sendMessage(eq(ID), eq(STATUS_SUCCESSFUL)),
                () -> verify(messageService).sendMessageToVolunteers(eq(VOL_NOTIFICATION_SUCCESSFUL), eq(ID))
        );
    }

}