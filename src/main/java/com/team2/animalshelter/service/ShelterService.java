package com.team2.animalshelter.service;

import com.team2.animalshelter.dto.in.ShelterDtoIn;
import com.team2.animalshelter.dto.out.ShelterDtoOut;
import com.team2.animalshelter.entity.Shelter;
import com.team2.animalshelter.mapper.ShelterMapper;
import com.team2.animalshelter.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;
    private final ImageService imageService;
    public static final String SHELTER_BUCKET = "shelters";
    @Value("${server.port}")
    private int port;

    public Optional<ShelterDtoOut> getShelter() {
        return shelterRepository.findAll().stream()
                .findFirst()
                .map(shelterMapper::toDto);
    }

    @Transactional
    public Optional<ShelterDtoOut> update(ShelterDtoIn shelterDtoIn, MultipartFile image) {
        return shelterRepository.findAll().stream()
                .findFirst()
                .map(shelter -> {
                    uploadImage(image, shelter);
                    return shelterMapper.toEntity(shelterDtoIn, shelter);
                })
                .map(shelterRepository::saveAndFlush)
                .map(shelterMapper::toDto);
    }

    public Optional<byte[]> getImage() {
        return shelterRepository.findAll().stream()
                .findFirst()
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
                            .port(port)
                            .pathSegment("api", "v1", "shelters", "map")
                            .toUriString()
            );
        }
    }

}
