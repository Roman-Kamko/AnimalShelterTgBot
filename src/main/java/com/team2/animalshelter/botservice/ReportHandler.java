package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Message;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.entity.Owner;
import com.team2.animalshelter.repository.OwnerRepository;
import com.team2.animalshelter.service.AdaptationService;
import com.team2.animalshelter.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReportHandler {

    private final OwnerRepository ownerRepository;
    private final ReportService reportService;
    private final AdaptationService adaptationService;
    private final MessageService messageService;

    /**
     * Обработка отчета. Отчет может отправить только {@link Owner}, не более одного в сутки. При успешной отправки отчет
     * пересылается волонтерам для его анализа.
     *
     * @param message сообщение.
     */
    public void handle(Message message) {
        var ownersId = ownerRepository.findAll().stream()
                .map(Owner::getTelegramId)
                .toList();
        var id = message.from().id();
        if (ownersId.contains(id)) {
            if (message.caption() != null) {
                if (adaptationService.findLastReportDate(id).equals(LocalDate.now())) {
                    messageService.sendMessage(id, InformationConstants.REPORT_EXIST);
                    return;
                }
                var report = reportService.createFromTelegram(message);
                messageService.sendMessage(id, InformationConstants.REPORT_CREATED);
                messageService.sendForwardMessageToVolunteers(message);
                messageService.sendMessageToVolunteers(buildInfo(report));
            } else {
                messageService.sendMessage(id, InformationConstants.NO_CAPTION);
            }
        } else {
            messageService.sendMessage(id, InformationConstants.NOT_OWNER);
        }
    }

    private String buildInfo(ReportDtoOut report) {
        return "id отчета: %d".formatted(report.getId());
    }

}


