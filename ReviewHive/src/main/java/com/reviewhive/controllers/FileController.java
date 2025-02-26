package com.reviewhive.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reviewhive.common.util.ApplicationPropertiesRead;

@Controller
public class FileController {

	@GetMapping(value = "/view/image/{questionNo}/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] responseImageJpg(@PathVariable String questionNo, @PathVariable String imageName) {
    	
		String imageDirectory = ApplicationPropertiesRead.getProperty("question.image.path");
		
		Path questionImagePath = Paths.get(imageDirectory, questionNo, imageName);
        
		Path noImgFileName = Paths.get(imageDirectory, "no-image.png");
        try {
            if (imageName == null || !Files.exists(questionImagePath)) {
                return Files.readAllBytes(noImgFileName);
            } else {
                return Files.readAllBytes(questionImagePath);
            }
        } catch (IOException e) {
        	
            e.printStackTrace();
            
            return new byte[0]; 
        }
	}
}
