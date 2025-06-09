package com.gabrieldears.talent_forge.domain.port;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStoragePort {
    String store(MultipartFile file, String fileName) throws IOException;
}
