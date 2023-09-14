package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.team2.animalshelter.botservice.FaqCommand.*;
import static com.team2.animalshelter.botservice.FaqCommand.RULES;

@Component
@RequiredArgsConstructor
public class FaqCommandHandler {

    private final Map<String, Consumer<Chat>> faqCommandExecutor = new HashMap<>();
    private final MessageService messageService;

    /**
     * Метод для регистрации команд. Для того что бы зарегистрировать команду положите в
     * {@link #faqCommandExecutor} команду, передварительно созданную в {@link FaqCommand},
     * а в виде значения ссылку на метод, который необходимо создать в этом классе.
     */
    @PostConstruct
    private void initMethod() {
        faqCommandExecutor.put(RULES.getText(), this::sendRules);
        faqCommandExecutor.put(DOC_LIST.getText(), this::sendDocList);
        faqCommandExecutor.put(DENIAL_REASONS.getText(), this::sendDenialReasons);
        faqCommandExecutor.put(CAT_TRANSPORTATION.getText(), this::sendCatTransportation);
        faqCommandExecutor.put(HOME_FOR_KITTY.getText(), this::sendHomeForKitty);
        faqCommandExecutor.put(HOME_FOR_ADULT_CAT.getText(), this::sendHomeForAdultCat);
        faqCommandExecutor.put(HOME_FOR_DIS_CAT.getText(), this::sendHomeForDisCat);
        faqCommandExecutor.put(DOG_TRANSPORTATION.getText(), this::sendDogTransportation);
        faqCommandExecutor.put(HOME_FOR_PUPPY.getText(), this::sendHomeForPuppy);
        faqCommandExecutor.put(HOME_FOR_ADULT_DOG.getText(), this::sendHomeForAdultDog);
        faqCommandExecutor.put(HOME_FOR_DIS_DOG.getText(), this::sendHomeForDisDog);
        faqCommandExecutor.put(CYNOLOGIST_ADVISE.getText(), this::sendCynologistAdvice);
        faqCommandExecutor.put(PROVEN_CYNOLOGISTS.getText(), this::sendProvenCynologists);
    }

    /**
     * Метод для обработки команд. В зависимости от поступившей команды будет выполнен соответствующий метод.
     *
     * @param faqText поступившая команда.
     * @param chat    из какого чата поступила команда.
     */
    public void handle(String faqText, Chat chat) {
        var faqCommands = FaqCommand.values();
        if (faqCommandExecutor.containsKey(faqText)) {
            for (FaqCommand command : faqCommands) {
                if (command.getText().equals(faqText)) {
                    faqCommandExecutor.get(command.getText()).accept(chat);
                    return;
                }
            }
        }
    }

    private void sendRules(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.DATING_RULES);
    }

    private void sendDocList(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.DOC_LIST);
    }

    private void sendDenialReasons(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.DENIAL_REASONS);
    }

    private void sendCatTransportation(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.CAT_TRANSPORTATION);
    }

    private void sendHomeForKitty(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.HOME_FOR_KITTY);
    }

    private void sendHomeForAdultCat(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.HOME_FOR_ADULT_CAT);
    }

    private void sendHomeForDisCat(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.HOME_FOR_DISABLED_CAT);
    }

    private void sendDogTransportation(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.DOG_TRANSPORTATION);
    }

    private void sendHomeForPuppy(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.HOME_FOR_PUPPY);
    }

    private void sendHomeForAdultDog(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.HOME_FOR_ADULT_DOG);
    }

    private void sendHomeForDisDog(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.HOME_FOR_DISABLED_DOG);
    }

    private void sendCynologistAdvice(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.CYNOLOGIST_ADVICE);
    }

    private void sendProvenCynologists(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.PROVEN_CYNOLOGISTS);
    }

}
