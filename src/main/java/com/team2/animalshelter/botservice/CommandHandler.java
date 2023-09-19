package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.exception.ShelterNotFoundException;
import com.team2.animalshelter.service.ShelterService;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.team2.animalshelter.botservice.Command.*;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final Map<String, Consumer<Chat>> commandExecutor = new HashMap<>();
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;
    private final ShelterService shelterService;

    /**
     * Метод для регистрации команд. Для того что бы зарегистрировать команду положите в
     * {@link #commandExecutor} команду, передварительно созданную в {@link Command},
     * а в виде значения ссылку на метод, который необходимо создать в этом классе.
     */
    @PostConstruct
    private void initMethod() {
        commandExecutor.put(START.getText(), this::showGreetings);
        commandExecutor.put(MAIN_MENU.getText(), this::showMainMenu);
        commandExecutor.put(SHELTER_MENU.getText(), this::showShelterMenu);
        commandExecutor.put(FAQ.getText(), this::showFaqMenu);
        commandExecutor.put(CHOOSE_ANIMAL_TYPE.getText(), this::showChooseAnimalMenu);
        commandExecutor.put(SHELTER_CONTACT.getText(), this::showShelterContact);
        commandExecutor.put(SHELTER_ADDRESS.getText(), this::showShelterAddress);
        commandExecutor.put(TIME_TABLE.getText(), this::showTimeTable);
        commandExecutor.put(SAFETY_PRECAUTIONS.getText(), this::showSafetyPrecautions);
        commandExecutor.put(SEND_CONTACT.getText(), this::sendContactRequest);
        commandExecutor.put(RULES.getText(), this::sendRules);
        commandExecutor.put(DOC_LIST.getText(), this::sendDocList);
        commandExecutor.put(DENIAL_REASONS.getText(), this::sendDenialReasons);
        commandExecutor.put(CAT_TRANSPORTATION.getText(), this::sendCatTransportation);
        commandExecutor.put(HOME_FOR_KITTY.getText(), this::sendHomeForKitty);
        commandExecutor.put(HOME_FOR_ADULT_CAT.getText(), this::sendHomeForAdultCat);
        commandExecutor.put(HOME_FOR_DIS_CAT.getText(), this::sendHomeForDisCat);
        commandExecutor.put(DOG_TRANSPORTATION.getText(), this::sendDogTransportation);
        commandExecutor.put(HOME_FOR_PUPPY.getText(), this::sendHomeForPuppy);
        commandExecutor.put(HOME_FOR_ADULT_DOG.getText(), this::sendHomeForAdultDog);
        commandExecutor.put(HOME_FOR_DIS_DOG.getText(), this::sendHomeForDisDog);
        commandExecutor.put(CYNOLOGIST_ADVISE.getText(), this::sendCynologistAdvice);
        commandExecutor.put(PROVEN_CYNOLOGISTS.getText(), this::sendProvenCynologists);
    }

    /**
     * Метод для обработки команд. В зависимости от поступившей команды будет выполнен соответствующий метод.
     *
     * @param navigationText поступившая команда.
     * @param chat           из какого чата поступила команда.
     */
    public void handle(String navigationText, Chat chat) {
        var navigationCommands = Command.values();
        if (commandExecutor.containsKey(navigationText)) {
            for (Command command : navigationCommands) {
                if (command.getText().equals(navigationText)) {
                    commandExecutor.get(command.getText()).accept(chat);
                    return;
                }
            }
        }
    }

    private void showGreetings(Chat chat) {
        if (!userService.isRegistered(chat.id())) {
            userService.create(chat);
            keyboardService.sendGreetings(chat.id());
        } else {
            showMainMenu(chat);
        }
    }

    private void showMainMenu(Chat chat) {
        keyboardService.sendMainMenu(chat.id());
    }

    private void showShelterMenu(Chat chat) {
        keyboardService.sendShelterMenu(chat.id());
    }

    private void showFaqMenu(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.FAQ_COMMAND);
    }

    private void showChooseAnimalMenu(Chat chat) {
        keyboardService.sendChooseAnimalMenu(chat.id());
    }

    private void showShelterContact(Chat chat) {
        var phone = shelterService.getShelter()
                .map(ShelterDtoOut::getPhoneNumber)
                .orElse(null);
        messageService.sendMessage(chat.id(), phone);
    }

    private void showShelterAddress(Chat chat) {
        var shelter = shelterService.getShelter();
        var address = shelter
                .map(ShelterDtoOut::getAddress)
                .orElse(null);
        var drivingDirections = shelter
                .map(ShelterDtoOut::getDrivingDirections)
                .orElse(null);
        messageService.sendMessage(chat.id(), address);
        var path = "image/" + ShelterService.SHELTER_BUCKET + "/" + drivingDirections;
        messageService.sendPhoto(chat.id(), path);
    }

    private void showTimeTable(Chat chat) {
        var timeTable = shelterService.getShelter().stream()
                .map(ShelterDtoOut::getTimeTable)
                .findFirst()
                .orElseThrow(ShelterNotFoundException::new);
        messageService.sendMessage(chat.id(), timeTable);
    }

    private void showSafetyPrecautions(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.SAFETY_PRECAUTIONS);
    }

    private void sendContactRequest(Chat chat) {
        messageService.sendMessage(chat.id(), InformationConstants.CONTACT_REQUEST);
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
