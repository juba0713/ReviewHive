package com.reviewhive.model.dao.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomQuestionEntity {
	
	public RandomQuestionEntity(Object[] objects) {
		
		this(
			(Integer) objects[0],
			(String) objects[1],
			(String) objects[2],
			null
		);
		
	}
	
	private int id;
	
	private String question;
	
	private String questionImage;
	
	private List<AnswerDetailsEntity> answers;
}
