package com.instasathi.university.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${app.upload.dir}")
    private String uploadDir;

    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "image" : file.getOriginalFilename());
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot >= 0) ext = original.substring(dot);

        Path folder = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(folder);

        String fileName = UUID.randomUUID() + ext;
        Path target = folder.resolve(fileName);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}
