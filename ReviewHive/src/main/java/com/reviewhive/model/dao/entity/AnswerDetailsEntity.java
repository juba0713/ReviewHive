package com.reviewhive.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/22/2024
 */
@Data
@AllArgsConstructor
public class AnswerDetailsEntity {
	
	public AnswerDetailsEntity(Object[] objects) {
		this(
			(Integer) objects[0],
			(Integer) objects[1],
			(String) objects[2],
			(Boolean) objects[3],
			(String) objects[4]
		);
	}

	private int id;
	
	private int questionId;
	
	private String answer;
	
	private Boolean isCorrect;
	
	private String answerImage;
}
