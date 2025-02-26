package com.reviewhive.model.dao.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Data
@AllArgsConstructor
public class QuestionaireDetailsEntity {

	public QuestionaireDetailsEntity(Object[] objects) {
		this(
			(Integer) objects[0],
			(Integer) objects[1],
			(String) objects[2],
			(String) objects[3],
			(String) objects[4],
			(String) objects[5],
			(Boolean) objects[6],
			(String) objects[7],
			(Timestamp) objects[8],
			(Timestamp) objects[9],
			(Integer) objects[10],
			(Integer) objects[11]
		);
	}
	
	private int id;
	
	private int categoryId;
	
	private String categoryName;
	
	private String questionaireName;
	
	private String abbreviation;
	
	private String desription;
	
	private Boolean isOpen;
	
	private String hexColor;
	
	private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
	private int totalPage;
	
	private int questionTotal;
}
