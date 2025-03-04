package com.reviewhive.model.services.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class GoogleAuthService {
    
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static Credential getCredentials() throws IOException {
        // Load credentials.json from classpath
        try (InputStream credentialsStream = new ClassPathResource("credentials.json").getInputStream()) {
            return GoogleCredential.fromStream(credentialsStream)
                    .createScoped(Collections.singleton("https://www.googleapis.com/auth/drive.file"));
        }
    }
}
