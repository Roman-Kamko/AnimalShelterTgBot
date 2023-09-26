package com.team2.animalshelter.scheduler;

import com.team2.animalshelter.botservice.MessageService;
import com.team2.animalshelter.entity.Adaptation;
import com.team2.animalshelter.entity.Owner;
import com.team2.animalshelter.repository.AdaptationRepository;
import com.team2.animalshelter.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.team2.animalshelter.botservice.InformationConstants.*;
import static com.team2.animalshelter.entity.enums.AdaptationStatus.*;

@Component
@RequiredArgsConstructor
public final class BotScheduler {

    private final OwnerRepository ownerRepository;
    private final AdaptationRepository adaptationRepository;
    private final MessageService messageService;

    /**
     * Рассылка уведомлений опекунам об изменении статуса адаптации. Так же уведомляет волонтеров
     * об изменении статуса опекуна.
     */
    @Scheduled(cron = "@daily")
    public void sendNotificationAboutAdaptationStatus() {
        var adaptations = adaptationRepository.findAllByAdaptationStatus(NOT_SUCCESSFUL, EXTENDED, SUCCESSFUL);
        for (Adaptation adaptation : adaptations) {
            final var ownerId = adaptation.getOwner().getTelegramId();
            switch (adaptation.getAdaptationStatus()) {
                case NOT_SUCCESSFUL -> {
                    messageService.sendMessage(ownerId, STATUS_NOT_SUCCESSFUL);
                    messageService.sendMessageToVolunteers(VOL_NOTIFICATION_NOT_SUCCESSFUL, ownerId);
                }
                case EXTENDED -> {
                    messageService.sendMessage(ownerId, STATUS_EXTENDED);
                    messageService.sendMessageToVolunteers(VOL_NOTIFICATION_EXTENDED, ownerId);
                }
                case SUCCESSFUL -> {
                    messageService.sendMessage(ownerId, STATUS_SUCCESSFUL);
                    messageService.sendMessageToVolunteers(VOL_NOTIFICATION_SUCCESSFUL, ownerId);
                }
            }
        }
    }

    /**
     * Уведомляет волонтеров о том что адаптационный период подошел к концу.
     */
    @Scheduled(cron = "@daily")
    public void successfulAdaptation() {
        for (Adaptation adaptation : adaptationRepository.findAllWhereEndDateEqualsLastReportDate()) {
            messageService.sendMessageToVolunteers(END_ADAPTATION, adaptation.getOwner().getTelegramId());
        }
    }

    /**
     * Рассылает уведомление опекунам в случае если они не отправляли отчет 2 дня подряд.
     * Так же уведомляет волонтеров о проблемных опекунах.
     */
    @Scheduled(cron = "@daily")
    public void sendAdaptationWarning() {
        for (Owner owner : ownerRepository.findAll()) {
            if (adaptationRepository.isExpiredAdaptation(owner.getTelegramId())) {
                messageService.sendMessage(owner.getTelegramId(), ADAPTATION_WARN);
                messageService.sendMessageToVolunteers(VOL_ADAPTATION_WARN, owner.getTelegramId());
            }
        }
    }

}
