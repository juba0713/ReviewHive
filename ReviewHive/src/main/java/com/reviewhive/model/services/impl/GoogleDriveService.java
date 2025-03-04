package com.reviewhive.model.services.impl;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.reviewhive.common.config.GoogleDriveConfig;

import java.io.IOException;
import java.util.Collections;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleDriveService {
    private final Drive driveService;

    public GoogleDriveService(GoogleDriveConfig googleDriveConfig) throws IOException, GeneralSecurityException {
        this.driveService = googleDriveConfig.getDriveService();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        // Convert MultipartFile to a temp file
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList("1lQ98dUVJcBEJIyPsW0KYw8ttn6wzzTby")); // Set your folder ID

        FileContent mediaContent = new FileContent(file.getContentType(), tempFile.toFile());
        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        Files.delete(tempFile); // Clean up temporary file
        return uploadedFile.getId(); // Return the file ID to store in the database
    }
}


