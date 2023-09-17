package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.OwnerDto;
import com.team2.animalshelter.entity.Owner;
import com.team2.animalshelter.entity.User;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.mapper.OwnerMapper;
import com.team2.animalshelter.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OwnerService {

    private final UserService userService;
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public Optional<OwnerDto> findById(Long id) {
        return ownerRepository.findById(id)
                .map(ownerMapper::toDto);
    }

    public List<OwnerDto> findAll() {
        return ownerRepository.findAll().stream()
                .map(ownerMapper::toDto)
                .collect(toList());
    }

    /**
     * Создать опекуна на основе данных пользователя.
     *
     * @param id идентификатор {@link User} на основе которого необходимо создать {@link Owner}.
     * @return экземпляр {@link OwnerDto}.
     * @throws EntityCreateException если возникла ошибки при сохранении сущности.
     */
    @Transactional
    public OwnerDto create(Long id) {
        return userService.findById(id)
                .map(ownerMapper::toEntity)
                .map(ownerRepository::save)
                .map(ownerMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public Optional<OwnerDto> update(Long id, OwnerDto OwnerDto) {
        return ownerRepository.findById(id)
                .map(owner -> ownerMapper.toEntity(OwnerDto, owner))
                .map(ownerRepository::saveAndFlush)
                .map(ownerMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return ownerRepository.findById(id)
                .map(owner -> {
                    ownerRepository.delete(owner);
                    ownerRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
