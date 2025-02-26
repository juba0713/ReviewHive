package com.reviewhive.model.dao.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionaireUserEntity {
	
	public QuestionaireUserEntity(Object[] objects) {
		this(
			(Integer) objects[0],
			(String) objects[1],
			(String) objects[2],
			(String) objects[3],
			(String) objects[4]
		);
	}
	
	private int id;
	
	private String abbreviation;
	
	private String questionaireName;
	
	private String hexColor;
	
	private String description;
}
