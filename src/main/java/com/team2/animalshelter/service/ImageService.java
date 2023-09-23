package com.team2.animalshelter.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

    @Value("${app.image.bucket}")
    private String bucket;

    @SneakyThrows
    public void upload(String imagePath, String specialBucket, InputStream content) {
        var fullPath = Path.of(bucket, specialBucket, imagePath);
        try (content) {
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public void upload(String imagePath, String specialBucket, byte[] image) {
        var fullPath = Path.of(bucket, specialBucket, imagePath);
        Files.createDirectories(fullPath.getParent());
        Files.write(fullPath, image, CREATE, TRUNCATE_EXISTING);
    }

    @SneakyThrows
    public Optional<byte[]> getImage(String imagePath, String specialBucket) {
        var fullPath = Path.of(bucket, specialBucket, imagePath);
        return Files.exists(fullPath)
                ? Optional.of(Files.readAllBytes(fullPath))
                : Optional.empty();
    }

}
