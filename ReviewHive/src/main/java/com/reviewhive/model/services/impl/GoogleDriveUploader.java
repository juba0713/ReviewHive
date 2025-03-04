package com.reviewhive.model.services.impl;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.client.http.InputStreamContent;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


public class GoogleDriveUploader {
//	public static String uploadFile(String accessToken, MultipartFile multipartFile) throws IOException, GeneralSecurityException {
//        Drive driveService = GoogleDriveService.getDriveService(accessToken);
//
//        // Convert MultipartFile to InputStreamContent
//        InputStreamContent mediaContent = new InputStreamContent(
//                multipartFile.getContentType(),
//                multipartFile.getInputStream()
//        );
//
//        File fileMetadata = new File();
//        fileMetadata.setName(multipartFile.getOriginalFilename()); // Set filename
//        fileMetadata.setParents(Collections.singletonList("YOUR_GOOGLE_DRIVE_FOLDER_ID")); // Optional: Set folder ID
//
//        File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
//                .setFields("id")
//                .execute();
//
//        return "File uploaded successfully! File ID: " + uploadedFile.getId();
//    }
}
