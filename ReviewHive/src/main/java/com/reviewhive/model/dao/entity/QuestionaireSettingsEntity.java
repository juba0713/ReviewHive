package com.reviewhive.model.dao.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionaireSettingsEntity {

	public QuestionaireSettingsEntity(Object[] objects) {
		this(
			(Boolean) objects[0],
			(Boolean) objects[1],
			(Boolean) objects[2],
			(Boolean) objects[3],
			(Boolean) objects[4],
			(String) objects[5],
			(String) objects[6],
			(String) objects[7]
		);
	}
	
	private Boolean showAnswerFlg;
	
	private Boolean showResultFlg;
	
	private Boolean answerRequiredFlg;
	
	private Boolean enablePreviousFlg; 
	
	private Boolean enableTimerFlg;
	
	private String hour;
	
	private String minute;
	
	private String second;

}
