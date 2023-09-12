package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.Shelter;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.mapper.ShelterMapper;
import com.team2.animalshelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

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
    private static final String SHELTER_BUCKET = "shelters";

    public Optional<ShelterDtoOut> findById(Long id) {
        return shelterRepository.findById(id)
                .map(shelterMapper::toDto);
    }

    public Optional<ShelterDtoOut> findByUserId(Long id) {
        return shelterRepository.findShelterByUsers_telegramId(id)
                .map(shelterMapper::toDto);
    }

    public List<ShelterDtoOut> findAll() {
        return shelterRepository.findAll().stream()
                .map(shelterMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public ShelterDtoOut create(ShelterDtoIn shelterDtoIn, MultipartFile image) {
        return Optional.of(shelterDtoIn)
                .map(shelterMapper::toEntity)
                .map(shelter -> {
                    shelterRepository.saveAndFlush(shelter);
                    uploadImage(image, shelter);
                    return shelterRepository.save(shelter);
                })
                .map(shelterMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public Optional<ShelterDtoOut> update(Long id, ShelterDtoIn shelterDtoIn, MultipartFile image) {
        return shelterRepository.findById(id)
                .map(shelter -> {
                    uploadImage(image, shelter);
                    return shelterMapper.toEntity(shelterDtoIn, shelter);
                })
                .map(shelterRepository::saveAndFlush)
                .map(shelterMapper::toDto);
    }

    @Transactional
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
                .flatMap(path -> imageService.getImage(path, SHELTER_BUCKET));
    }

    /**
     * Загрузить картинку на сервер.
     *
     * @param image {@link MultipartFile}.
     */
    @SneakyThrows
    private void uploadImage(MultipartFile image, Shelter shelter) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), SHELTER_BUCKET, image.getInputStream());
            shelter.setDrivingDirections(image.getOriginalFilename());
            shelter.setDrivingDirectionsUrl(
                    UriComponentsBuilder.newInstance()
                            .scheme("http")
                            .host("localhost")
                            .port("8081")
                            .pathSegment("api", "v1", "shelters", String.valueOf(shelter.getId()), "map")
                            .toUriString()
            );
        }
    }

}
