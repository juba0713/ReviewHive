package com.reviewhive.model.objects;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QuestionObj {
	
	private int questionId;
	
	private String questionType;
	
	private String question;
	
	private MultipartFile questionImage;
	
	private List<AnswerObj> answers;
	
	private Boolean hasModified;
	
	private Boolean hasQuestionImageModified;
	
	private Boolean hasDeleted;
	
	public boolean hasNullField() {
        return questionType == null || answers == null;
    }
}
