package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Класс для обработки команд
 */
@Component
@RequiredArgsConstructor
public class NavigationCommandHandler {

    private final Map<String, Consumer<Chat>> navigationCommandExecutor = new HashMap<>();
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;

    /**
     * Метод для регистрации команд. Для того что бы зарегистрировать команду положите в
     * {@link #navigationCommandExecutor} команду, передварительно созданную в {@link InformationConstants},
     * а в виде значения ссылку на метод, который необходимо создать в этом классе.
     */
    @PostConstruct
    private void initMethod() {
        navigationCommandExecutor.put(NavigationCommand.START.getText(), this::showGreetings);
        navigationCommandExecutor.put(NavigationCommand.MAIN_MENU.getText(), this::showMainMenu);
        navigationCommandExecutor.put(NavigationCommand.SHELTER_MENU.getText(), this::showShelterMenu);
        navigationCommandExecutor.put(NavigationCommand.FAQ.getText(), this::showFaqMenu);
        navigationCommandExecutor.put(NavigationCommand.CHOOSE_ANIMAL_TYPE.getText(), this::showChooseAnimalMenu);
        navigationCommandExecutor.put(NavigationCommand.SHELTER_CONTACT.getText(), this::showShelterContact);
        navigationCommandExecutor.put(NavigationCommand.SHELTER_ADDRESS.getText(), this::showShelterAddress);
        navigationCommandExecutor.put(NavigationCommand.TIME_TABLE.getText(), this::showTimeTable);
        navigationCommandExecutor.put(NavigationCommand.SAFETY_PRECAUTIONS.getText(), this::showSafetyPrecautions);
        navigationCommandExecutor.put(NavigationCommand.SEND_CONTACT.getText(), this::sendContact);

    }

    /**
     * Метод для обработки команд. В зависимости от поступившей команды будет выполнен соответствующий метод,
     * если поступившая команда не зарегистрирована в {@link #initMethod()}, то будет выдано сообщение
     * {@value InformationConstants#UNKNOWN_COMMAND}.
     *
     * @param navigationText поступившая команда.
     * @param chat           из какого чата поступила команда.
     */
    public void handle(String navigationText, Chat chat) {
        var navigationCommands = NavigationCommand.values();
        if (navigationCommandExecutor.containsKey(navigationText)) {
            for (NavigationCommand command : navigationCommands) {
                if (command.getText().equals(navigationText)) {
                    navigationCommandExecutor.get(command.getText()).accept(chat);
                    return;
                }
            }
        } else {
            messageService.sendUnknownCommand(chat.id());
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
        messageService.sendFaqMessage(chat.id());
    }

    private void showChooseAnimalMenu(Chat chat) {
        keyboardService.sendChooseAnimalMenu(chat.id());
    }

    private void showShelterContact(Chat chat) { messageService.sendShelterContact(chat.id());
    }

    private void showShelterAddress(Chat chat) {
//String imageCaption="г. Астана, ул. Лесная, д. 3.";
//String imagePath="src/main/resources/address.jpg";
        messageService.sendShelterAddress(chat.id());
    }

    private void showTimeTable(Chat chat) { messageService.sendTimeTable(chat.id());
    }

    private void showSafetyPrecautions(Chat chat) {
        messageService.sendSafetyPrecautions(chat.id());
    }

    private void sendContact(Chat chat) { messageService.sendContact(chat.id());
    }

}
