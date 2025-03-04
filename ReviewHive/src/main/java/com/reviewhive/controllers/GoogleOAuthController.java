package com.reviewhive.controllers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.drive.DriveScopes;

@RestController
@RequestMapping("/oauth")
public class GoogleOAuthController {

    private static final String CLIENT_ID = "274648807175-k6n61ijgfk6r15nfpb7l86987ai8a88h.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "https://reviewhive.onrender.com/oauth/callback";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

    @GetMapping("/authorize")
    public ResponseEntity<?> authorize() {
    
        String authUrl = "https://accounts.google.com/o/oauth2/auth?" +
                "client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code" +
                "&scope=" + String.join(" ", SCOPES) +
                "&access_type=offline";
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, authUrl).build();
    }
}

