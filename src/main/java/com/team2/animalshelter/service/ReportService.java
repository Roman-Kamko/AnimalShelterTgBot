package com.team2.animalshelter.service;

import com.pengrad.telegrambot.model.Message;
import com.team2.animalshelter.botservice.MessageService;
import com.team2.animalshelter.dto.in.ReportDtoIn;
import com.team2.animalshelter.dto.out.ReportDtoOut;
import com.team2.animalshelter.entity.Report;
import com.team2.animalshelter.exception.EntityCreateException;
import com.team2.animalshelter.mapper.ReportMapper;
import com.team2.animalshelter.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
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
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final ImageService imageService;
    private final MessageService messageService;

    public static final String REPORT_BUCKET = "reports";
    @Value("${server.port}")
    private int port;

    public Optional<ReportDtoOut> findById(Long id) {
        return reportRepository.findById(id)
                .map(reportMapper::toDto);
    }

    public List<ReportDtoOut> findAll() {
        return reportRepository.findAll().stream()
                .map(reportMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public ReportDtoOut create(ReportDtoIn reportDtoIn, MultipartFile image) {
        return Optional.of(reportDtoIn)
                .map(reportMapper::toEntity)
                .map(report -> {
                    reportRepository.saveAndFlush(report);
                    uploadImage(image, report);
                    return reportRepository.save(report);
                })
                .map(reportMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public ReportDtoOut createFromTelegram(Message message) {
        return Optional.of(message)
                .map(reportMapper::toEntity)
                .map(report -> {
                    reportRepository.saveAndFlush(report);
                    uploadImage(report);
                    return reportRepository.save(report);
                })
                .map(reportMapper::toDto)
                .orElseThrow(EntityCreateException::new);
    }

    @Transactional
    public Optional<ReportDtoOut> update(Long id, ReportDtoIn reportDtoIn, MultipartFile image) {
        return reportRepository.findById(id)
                .map(report -> {
                    uploadImage(image, report);
                    return reportMapper.toEntity(reportDtoIn, report);
                })
                .map(reportRepository::saveAndFlush)
                .map(reportMapper::toDto);
    }

    @Transactional
    public boolean delete(Long id) {
        return reportRepository.findById(id)
                .map(report -> {
                    reportRepository.delete(report);
                    reportRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Optional<byte[]> getImage(Long id) {
        return reportRepository.findById(id)
                .map(Report::getPhoto)
                .filter(StringUtils::hasText)
                .flatMap(path -> imageService.getImage(path, REPORT_BUCKET));
    }

    /**
     * Загрузить картинку на сервер.
     *
     * @param image {@link MultipartFile}.
     */
    @SneakyThrows
    private void uploadImage(MultipartFile image, Report report) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), REPORT_BUCKET, image.getInputStream());
            report.setPhoto(image.getOriginalFilename());
            report.setPhotoUrl(buildUrl(report));
        }
    }

    /**
     * Загрузка картинки на сервер из телеграма.
     *
     * @param report {@link Report}.
     */
    @SneakyThrows
    private void uploadImage(Report report) {
        messageService.getPhoto(report).ifPresent(image -> {
            imageService.upload(report.getPhoto(), REPORT_BUCKET, image);
            report.setPhotoUrl(buildUrl(report));
        });
    }

    @NotNull
    private String buildUrl(Report report) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .pathSegment("api", "v1", "reports", String.valueOf(report.getId()), "photo")
                .toUriString();
    }
}
