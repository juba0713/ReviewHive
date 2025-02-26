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
 * @added 12/17/2024
 */
@Data
@Entity
@Table(name="m_question")
public class QuestionEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int questionaireId;
	
	@Column(nullable=false, columnDefinition="TEXT")
	private String question;
	
	@Column(nullable=false)
	private String questionType;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isOpen;
	
	@Column(nullable = false)
	private Timestamp createdDate; 
	
	@Column(nullable = false)
	private Timestamp updatedDate;
	
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean deleteFlg;
	
	private String questionImage;
}
