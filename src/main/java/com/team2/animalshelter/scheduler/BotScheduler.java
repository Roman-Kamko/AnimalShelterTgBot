package com.team2.animalshelter.scheduler;

import com.team2.animalshelter.botservice.MessageService;
import com.team2.animalshelter.dto.out.AdaptationDtoOut;
import com.team2.animalshelter.entity.Owner;
import com.team2.animalshelter.repository.OwnerRepository;
import com.team2.animalshelter.service.AdaptationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

import static com.team2.animalshelter.botservice.InformationConstants.*;

@RequiredArgsConstructor
public final class BotScheduler {

    private final OwnerRepository ownerRepository;
    private final AdaptationService adaptationService;
    private final MessageService messageService;

    /**
     * Рассылка уведомлений опекунам об изменении статуса адаптации. Так же уведомляет волонтеров
     * об изменении статуса опекуна.
     */
    @Scheduled(cron = "@daily")
    private void sendNotificationAboutAdaptationStatus() {
        for (Owner owner : ownerRepository.findAll()) {
            for (AdaptationDtoOut adaptation : adaptationService.findAll()) {
                switch (adaptation.getAdaptationStatus()) {
                    case NOT_SUCCESSFUL -> {
                        messageService.sendMessage(owner.getTelegramId(), STATUS_NOT_SUCCESSFUL);
                        messageService.sendMessageToVolunteers(VOL_NOTIFICATION_NOT_SUCCESSFUL, owner.getTelegramId());
                    }
                    case EXTENDED -> {
                        messageService.sendMessage(owner.getTelegramId(), STATUS_EXTENDED);
                        messageService.sendMessageToVolunteers(VOL_NOTIFICATION_EXTENDED, owner.getTelegramId());
                    }
                    case SUCCESSFUL -> {
                        messageService.sendMessage(owner.getTelegramId(), STATUS_SUCCESSFUL);
                        messageService.sendMessageToVolunteers(VOL_NOTIFICATION_SUCCESSFUL, owner.getTelegramId());
                    }
                }
            }
        }
    }

    /**
     * Уведомляет волонтеров о том что адаптационный период подошел к концу.
     */
    @Scheduled(cron = "@daily")
    private void successfulAdaptation() {
        for (AdaptationDtoOut adaptation : adaptationService.findAll()) {
            if (adaptation.getEndDate().equals(LocalDate.now())) {
                messageService.sendMessageToVolunteers(END_ADAPTATION, adaptation.getOwner().getTelegramId());
            }
        }
    }

    /**
     * Рассылает уведомление опекунам в случае если они не отправляли отчет 2 дня подряд.
     * Так же уведомляет волонтеров о проблемных опекунах.
     */
    @Scheduled(cron = "@daily")
    private void sendAdaptationWarning() {
        int ignoredReportsDays = 2;
        for (Owner owner : ownerRepository.findAll()) {
            for (AdaptationDtoOut adaptation : adaptationService.findAll()) {
                var lastReportDate = adaptationService.findLastReportDate(owner.getTelegramId());
                if (!lastReportDate.isEqual(adaptation.getEndDate()) &&
                        lastReportDate.isBefore(LocalDate.now().minusDays(ignoredReportsDays))) {
                    messageService.sendMessage(owner.getTelegramId(), ADAPTATION_WARN);
                    messageService.sendMessageToVolunteers(VOL_ADAPTATION_WARN, owner.getTelegramId());
                }
            }

        }
    }

}
