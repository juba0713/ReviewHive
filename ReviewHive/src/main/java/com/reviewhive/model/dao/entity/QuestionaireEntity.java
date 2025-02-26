package com.reviewhive.model.dao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Data
@Entity
@Table(name="m_questionaire")
public class QuestionaireEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private int categoryId;
	
	@Column(nullable=false)
	private String questionaireName;
	
	@Column(nullable=false)
	private String abbreviation;
	
	@Column(nullable=false)
	private String description;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isOpen;
	
	@Column(nullable=false)
	private String hexColor; 
	
	@Column(nullable = false)
	private Timestamp createdDate;
	
	@Column(nullable = false)
	private Timestamp updatedDate; 
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean deleteFlg;
	
	private Boolean showAnswerFlg;
	
	private Boolean showResultFlg;
	
	private Boolean answerRequiredFlg;
	
	private Boolean enablePreviousFlg; 
	
	private Boolean enableTimerFlg;
	
	private String hour;
	
	private String minute;
	
	private String second;

	
}
