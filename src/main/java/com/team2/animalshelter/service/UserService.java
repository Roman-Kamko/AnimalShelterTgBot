package com.team2.animalshelter.service;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.entity.User;
import com.team2.animalshelter.exception.UserCreateException;
import com.team2.animalshelter.exception.UserNotFoundException;
import com.team2.animalshelter.mapper.UserCreateFromChatMapper;
import com.team2.animalshelter.mapper.UserMapper;
import com.team2.animalshelter.repository.UserRepository;
import com.team2.animalshelter.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCreateFromChatMapper userCreateFromChatMapper;
    private final UserMapper userMapper;
    private final EntityUtils<User> entityUtils;

    /**
     * Поиск пользователя по идентификатору.
     *
     * @param id идентификатор искомого пользователя.
     * @return {@code Optional<User>}.
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Метод определяющий зарегистрирован ли пользователь в системе.
     *
     * @param id идентификатор искомого пользователя.
     * @return true если пользователь зарегистрирован, иначе false.
     */
    public boolean isRegistered(Long id) {
        return userRepository.findById(id)
                .isPresent();
    }

    /**
     * Создание экземпляра сущности {@link User} на основе данных из {@link Chat} с последующим сохранением
     * пользователя в БД.
     *
     * @param chat чат между ботом и пользователем.
     * @return экземпляр сущности {@link User}.
     * @throws UserCreateException если возникла ошибки при сохранении пользователя.
     */
    @Transactional
    public UserDto create(Chat chat) {
        return Optional.of(chat)
                .map(userCreateFromChatMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(UserCreateException::new);
    }

    /**
     * Метод для сохранения внесенных изменений в пользователя. Изменения вносятся при помощи
     * вспомогательного метода {@link EntityUtils#copyNonNullFields(Object, Object)}.
     *
     * @param id          идентификатор пользователя, которого нужно изменить.
     * @param updatedUser пользователь с измененными данными.
     * @return экземпляр сущности {@link User}.
     * @throws UserNotFoundException если пользователь не найден по id
     */
    @Transactional
    public UserDto update(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> entityUtils.copyNonNullFields(updatedUser, user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
