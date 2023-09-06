package com.team2.animalshelter.mapper;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserCreateFromChatMapper {

    /**
     * Создание экземпляра сущности {@link User} на основе данных из {@link Chat}.
     *
     * @param chat чат пользователя с ботом.
     * @return экземпляра сущности {@link User}.
     */
    public User toEntity(Chat chat) {
        var user = new User();
        user.setTelegramId(chat.id());
        user.setUsername(chat.username());
        user.setFirstname(chat.firstName());
        user.setLastname(chat.lastName());
        return user;
    }

}