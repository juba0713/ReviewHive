package com.reviewhive.common.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveConfig {
//    private static final String APPLICATION_NAME = "Spring Boot Google Drive";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
//    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
//
//    public Drive getDriveService() throws IOException, GeneralSecurityException {
//        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        Credential credential = getCredentials(httpTransport);
//        return new Drive.Builder(httpTransport, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    private Credential getCredentials(HttpTransport httpTransport) throws IOException {
//        // Load client_secret.json from src/main/resources
//        InputStream in = new ClassPathResource(CREDENTIALS_FILE_PATH).getInputStream();
//        
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build(); 
//        
//        LocalServerReceiver receiver = new LocalServerReceiver.B uilder()
//                .setPort(8083) // Set your desired port
//                .setCallbackPath("/Callback") // Ensure callback path matches Google settings
//                .build();
//
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
//                .setHost("0.0.0.0") // Allows external connections
//                .setPort(443) // Use 443 for HTTPS
//                .setCallbackPath("/oauth2callback") // Ensure it matches Google redirect URI
//                .build();
//
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        
        //return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        
     // Listen on deployed domain, not localhost
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
//                .setHost("reviewhive.onrender.com") // Change this to your domain
//                .setPort(-1) // -1 disables local port binding
//                .build();
//
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        

  //  }
}
