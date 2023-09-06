package com.team2.animalshelter.botservice;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class NavigationCommandHandler {

    private final Map<String, Consumer<Chat>> navigationCommandExecutor = new HashMap<>();
    private final UserService userService;
    private final KeyboardService keyboardService;
    private final MessageService messageService;

    @PostConstruct
    private void initMethod() {
        navigationCommandExecutor.put(NavigationCommand.START.getText(), this::showGreetings);
        navigationCommandExecutor.put(NavigationCommand.MAIN_MENU.getText(), this::showMainMenu);
        navigationCommandExecutor.put(NavigationCommand.SHELTER_MENU.getText(), this::showShelterMenu);
        navigationCommandExecutor.put(NavigationCommand.FAQ.getText(), this::showFaqMenu);
        navigationCommandExecutor.put(NavigationCommand.CHOOSE_ANIMAL_TYPE.getText(), this::showChooseAnimalMenu);
    }

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

}
