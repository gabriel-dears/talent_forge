package com.gabrieldears.talent_forge.infrastructure.storage;

import com.gabrieldears.talent_forge.domain.port.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class LocalFileStorageService implements FileStoragePort {
    private final Path uploadPath;

    public LocalFileStorageService(@Value("${APP_UPLOAD_DIR}") String uploadDir) throws IOException {
        this.uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
    }

    @Override
    public String store(MultipartFile file, String fileName) throws IOException {
        Path targetPath = uploadPath.resolve(fileName);
        file.transferTo(targetPath.toFile());
        return targetPath.toString();
    }
}

