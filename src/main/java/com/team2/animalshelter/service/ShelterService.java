package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.Shelter;
import com.team2.animalshelter.exception.ShelterCreateException;
import com.team2.animalshelter.mapper.ShelterMapper;
import com.team2.animalshelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;
    private final ImageService imageService;

    public Optional<ShelterDtoOut> findById(Long id) {
        return shelterRepository.findById(id)
                .map(shelterMapper::toDto);
    }

    public List<ShelterDtoOut> findAll() {
        return shelterRepository.findAll().stream()
                .map(shelterMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public ShelterDtoOut create(ShelterDtoIn shelterDtoIn) {
        return Optional.of(shelterDtoIn)
                .map(shelterMapper::toEntity)
                .map(shelter -> {
                    uploadImage(shelterDtoIn.getImage());
                    return shelterRepository.save(shelter);
                })
                .map(shelterMapper::toDto)
                .orElseThrow(ShelterCreateException::new);
    }

    @Transactional
    public Optional<ShelterDtoOut> update(Long id, ShelterDtoIn shelterDtoIn) {
        return shelterRepository.findById(id)
                .map(shelter -> {
                    uploadImage(shelterDtoIn.getImage());
                    return shelterMapper.toEntity(shelterDtoIn, shelter);
                })
                .map(shelterRepository::saveAndFlush)
                .map(shelterMapper::toDto);
    }

    public boolean delete(Long id) {
        return shelterRepository.findById(id)
                .map(shelter -> {
                    shelterRepository.delete(shelter);
                    shelterRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<byte[]> getImage(Long id) {
        return shelterRepository.findById(id)
                .map(Shelter::getDrivingDirections)
                .filter(StringUtils::hasText)
                .flatMap(imageService::getImage);
    }

    /**
     * Загрузить картинку на сервер.
     *
     * @param image {@link MultipartFile}.
     */
    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

}
