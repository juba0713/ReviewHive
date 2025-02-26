package com.reviewhive.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@RestController
public class PingAPI {

	@GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK"); 
    }
}
