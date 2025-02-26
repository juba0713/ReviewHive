package com.reviewhive.model.objects;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AnswerObj {
	
	private int answerId;

	private String answerNo;
	
	private String answer;
	
	private MultipartFile answerImage;
	
	private Boolean isCorrect;
	
	private Boolean hasModified;
	
	private Boolean hasDeleted;
	
	private Boolean hasAnswerImageModified;
	
	public boolean hasNullField() {
        return answer == null || isCorrect == null;
    }
}
