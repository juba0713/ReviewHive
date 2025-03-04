package com.reviewhive.model.services.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleDriveService {
//    private final Drive driveService;
//
//    public GoogleDriveService(GoogleDriveConfig googleDriveConfig) throws IOException, GeneralSecurityException {
//        this.driveService = googleDriveConfig.getDriveService();
//    }
//
//    public String uploadFile(MultipartFile file) throws IOException {
//        // Convert MultipartFile to a temp file
//        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
//        Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
//
//        File fileMetadata = new File();
//        fileMetadata.setName(file.getOriginalFilename());
//        fileMetadata.setParents(Collections.singletonList("1lQ98dUVJcBEJIyPsW0KYw8ttn6wzzTby")); // Set your folder ID
//
//        FileContent mediaContent = new FileContent(file.getContentType(), tempFile.toFile());
//        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
//                .setFields("id")
//                .execute();
//
//        Files.delete(tempFile); // Clean up temporary file
//        return uploadedFile.getId(); // Return the file ID to store in the database
//    }
	
//	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//
//    public static Drive getDriveService(String accessToken) throws GeneralSecurityException, IOException {
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//
//        return new Drive.Builder(httpTransport, JSON_FACTORY, null)
//                .setHttpRequestInitializer(request -> request.getHeaders().setAuthorization("Bearer " + accessToken))
//                .setApplicationName("ReviewHive")
//                .build();
//    }
	
	private static final String APPLICATION_NAME = "ReviewHive";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static Drive driveService;

    public GoogleDriveService() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        driveService = new Drive.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(file.getOriginalFilename());
        fileMetadata.setParents(Collections.singletonList("1lQ98dUVJcBEJIyPsW0KYw8ttn6wzzTby")); // Google Drive Folder ID

        AbstractInputStreamContent content = new ByteArrayContent(file.getContentType(), file.getBytes());

        File uploadedFile = driveService.files().create(fileMetadata, content)
                .setFields("id")
                .execute();

        return uploadedFile.getId();
    }
}


