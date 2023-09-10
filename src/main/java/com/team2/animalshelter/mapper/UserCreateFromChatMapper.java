package com.team2.animalshelter.mapper;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateFromChatMapper {

    /**
     * Создание экземпляра сущности {@link User} на основе данных из {@link Chat}.
     *
     * @param fromObj чат пользователя с ботом.
     * @return экземпляра сущности {@link User}.
     */
    public User toEntity(Chat fromObj) {
        var toObj = new User();
        toObj.setTelegramId(fromObj.id());
        toObj.setUsername(fromObj.username());
        toObj.setFirstname(fromObj.firstName());
        toObj.setLastname(fromObj.lastName());
        return toObj;
    }

}
