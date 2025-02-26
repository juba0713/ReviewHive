package com.reviewhive.model.dao.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/22/2024
 */
@Data
@AllArgsConstructor
public class QuestionDetailsEntity {

	public QuestionDetailsEntity(Object[] objects) {
		this(
			(Integer) objects[0],
			(Integer) objects[1],
			(String) objects[2],
			(String) objects[3],
			(String) objects[4],
			null
		);
	}
	
	private int id;
	
	private int questionaireId;
	
	private String question;
	
	private String questionType;
	
	private String questionImage;
	
	private List<AnswerDetailsEntity> answers;
	
}
