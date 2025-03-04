package com.reviewhive.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

@RestController
@RequestMapping("/oauth")
public class GoogleOAuthCallbackController {

    private static final String CLIENT_ID = "your-client-id.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "your-client-secret";
    private static final String REDIRECT_URI = "https://reviewhive.onrender.com/oauth/callback";

    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(@RequestParam("code") String code) {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI)
                    .execute();

            // Get access & refresh tokens
            String accessToken = tokenResponse.getAccessToken();
            String refreshToken = tokenResponse.getRefreshToken();

            // Save tokens to your database or session
            return ResponseEntity.ok("Authentication successful! Access Token: " + accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

