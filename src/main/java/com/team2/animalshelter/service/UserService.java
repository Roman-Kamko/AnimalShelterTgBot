package com.team2.animalshelter.service;

import com.pengrad.telegrambot.model.Chat;
import com.team2.animalshelter.dto.UserDto;
import com.team2.animalshelter.entity.User;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.exception.UserNotFoundException;
import com.team2.animalshelter.mapper.UserCreateFromChatMapper;
import com.team2.animalshelter.mapper.UserMapper;
import com.team2.animalshelter.repository.UserRepository;
import com.team2.animalshelter.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCreateFromChatMapper userCreateFromChatMapper;
    private final UserMapper userMapper;
    private final EntityUtils<User> entityUtils;

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    public boolean isRegistered(Long id) {
        return userRepository.findById(id)
                .isPresent();
    }

    /**
     * Создание экземпляра сущности {@link User} на основе данных из {@link Chat} с последующим сохранением
     * пользователя в БД.
     *
     * @param chat чат между ботом и пользователем.
     * @return экземпляр {@link UserDto}.
     * @throws EntityCreateException если возникла ошибки при сохранении сущности.
     */
    @Transactional
    public UserDto create(Chat chat) {
        return Optional.of(chat)
                .map(userCreateFromChatMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        return Optional.of(userDto)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    /**
     * Метод для сохранения внесенных изменений в пользователя. Изменения вносятся при помощи
     * вспомогательного метода {@link EntityUtils#copyNonNullFields(Object, Object)}.
     *
     * @param userDto пользователь с измененными данными.
     * @return экземпляр {@link UserDto}.
     * @throws UserNotFoundException если пользователь не найден по id.
     */
    @Transactional
    public Optional<UserDto> update(UserDto userDto) {
        return userRepository.findById(userDto.getTelegramId())
                .map(user -> entityUtils.copyNonNullFields(userMapper.toEntity(userDto), user))
                .map(userRepository::saveAndFlush)
                .map(userMapper::toDto);
    }


    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
