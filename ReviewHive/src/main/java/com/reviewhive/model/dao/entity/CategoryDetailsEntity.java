package com.reviewhive.model.dao.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Data
@AllArgsConstructor
public class CategoryDetailsEntity {
	
	public CategoryDetailsEntity(Object[] objects) {
		this(
			(Integer) objects[0],
			(String) objects[1],
			(String) objects[2],
			(Boolean) objects[3],
			(String) objects[4],
			(Timestamp) objects[5],
			(Timestamp) objects[6],
			(Integer) objects[7]
		);
	}
	
	private int id;

	private String categoryName;

	private String description;

	private Boolean isOpen;

	private String hexColor;

	private Timestamp createdDate;

	private Timestamp updateDate;
	
	private int totalPage;
}
