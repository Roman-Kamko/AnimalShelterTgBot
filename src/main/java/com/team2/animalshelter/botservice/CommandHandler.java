package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Message;
import com.team2.animalshelter.dto.out.AnimalDtoOut;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.enums.AnimalType;
import com.team2.animalshelter.exception.ShelterNotFoundException;
import com.team2.animalshelter.service.AnimalService;
import com.team2.animalshelter.service.ShelterService;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.team2.animalshelter.botservice.Command.*;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final Map<String, Consumer<Message>> commandExecutor = new HashMap<>();
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;
    private final ShelterService shelterService;
    private final AnimalService animalService;


    /**
     * Метод для регистрации команд. Для того что бы зарегистрировать команду положите в
     * {@link #commandExecutor} команду, предварительно созданную в {@link Command},
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
        commandExecutor.put(CAT_SHELTER.getText(), this::sendCatList);
        commandExecutor.put(DOG_SHELTER.getText(), this::sendDogList);
        commandExecutor.put(CALL_A_VOLUNTEER.getText(), this::callVolunteer);
    }

    /**
     * Метод для обработки команд. В зависимости от поступившей команды будет выполнен соответствующий метод.
     *
     * @param message поступившее сообщение.
     */
    public void handle(String botCommand, Message message) {
        var commands = Command.values();
        if (commandExecutor.containsKey(botCommand)) {
            for (Command command : commands) {
                if (command.getText().equals(botCommand)) {
                    commandExecutor.get(command.getText()).accept(message);
                    return;
                }
            }
        }
    }

    private void showGreetings(Message message) {
        var chatId = message.chat().id();
        if (!userService.isRegistered(chatId)) {
            userService.create(message.chat());
            keyboardService.sendGreetings(chatId);
        } else {
            showMainMenu(message);
        }
    }

    private void showMainMenu(Message message) {
        keyboardService.sendMainMenu(message.chat().id());
    }

    private void showShelterMenu(Message message) {
        keyboardService.sendShelterMenu(message.chat().id());
    }

    private void showFaqMenu(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.FAQ_COMMAND);
    }

    private void showChooseAnimalMenu(Message message) {
        keyboardService.sendChooseAnimalMenu(message.chat().id());
    }

    private void showShelterContact(Message message) {
        var phone = shelterService.getShelter()
                .map(ShelterDtoOut::getPhoneNumber)
                .orElse(null);
        messageService.sendMessage(message.chat().id(), phone);
    }

    private void showShelterAddress(Message message) {
        var shelter = shelterService.getShelter();
        var address = shelter
                .map(ShelterDtoOut::getAddress)
                .orElse(null);
        var drivingDirections = shelter
                .map(ShelterDtoOut::getDrivingDirections)
                .orElse(null);
        messageService.sendMessage(message.chat().id(), address);
        var path = "image/" + ShelterService.SHELTER_BUCKET + "/" + drivingDirections;
        messageService.sendPhoto(message.chat().id(), path);
    }

    private void showTimeTable(Message message) {
        var timeTable = shelterService.getShelter().stream()
                .map(ShelterDtoOut::getTimeTable)
                .findFirst()
                .orElseThrow(ShelterNotFoundException::new);
        messageService.sendMessage(message.chat().id(), timeTable);
    }

    private void showSafetyPrecautions(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.SAFETY_PRECAUTIONS);
    }

    private void sendContactRequest(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.CONTACT_REQUEST);
    }

    private void sendRules(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.DATING_RULES);
    }

    private void sendDocList(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.DOC_LIST);
    }

    private void sendDenialReasons(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.DENIAL_REASONS);
    }

    private void sendCatTransportation(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.CAT_TRANSPORTATION);
    }

    private void sendHomeForKitty(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.HOME_FOR_KITTY);
    }

    private void sendHomeForAdultCat(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.HOME_FOR_ADULT_CAT);
    }

    private void sendHomeForDisCat(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.HOME_FOR_DISABLED_CAT);
    }

    private void sendDogTransportation(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.DOG_TRANSPORTATION);
    }

    private void sendHomeForPuppy(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.HOME_FOR_PUPPY);
    }

    private void sendHomeForAdultDog(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.HOME_FOR_ADULT_DOG);
    }

    private void sendHomeForDisDog(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.HOME_FOR_DISABLED_DOG);
    }

    private void sendCynologistAdvice(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.CYNOLOGIST_ADVICE);
    }

    private void sendProvenCynologists(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.PROVEN_CYNOLOGISTS);
    }

    private void sendCatList(Message message) {
        showAnimal(message, animalService.findAllWithoutOwner(AnimalType.CAT));
    }

    private void sendDogList(Message message) {
        showAnimal(message, animalService.findAllWithoutOwner(AnimalType.DOG));
    }

    /**
     * Конфигурирование вывода списка животных.
     *
     * @param message принятое сообщение.
     * @param animals список животных.
     */
    private void showAnimal(Message message, List<AnimalDtoOut> animals) {
        for (AnimalDtoOut animal : animals) {
            String view = """
                    Животное: %s,
                    Кличка: %s,
                    Возраст: %s,
                    Порода: %s
                    """
                    .formatted(
                            animal.getAnimalType().getTypeOfAnimal(),
                            animal.getName(),
                            animal.getAge(),
                            animal.getBreed()
                    );
            messageService.sendMessage(message.chat().id(), view);
        }
    }

    /**
     * Уведомляет пользователя о принятии запроса, а волонтеров о том что пользователю нужна помощь.
     *
     * @param message принятое сообщение.
     */
    private void callVolunteer(Message message) {
        messageService.sendMessage(message.chat().id(), InformationConstants.CALL_VOLUNTEER);
        messageService.sendForwardMessageToVolunteers(message);
    }

}
