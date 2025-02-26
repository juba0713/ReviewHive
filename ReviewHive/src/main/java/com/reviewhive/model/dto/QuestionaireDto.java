package com.reviewhive.model.dto;

import java.util.List;

import com.reviewhive.common.constant.MessageConstant;
import com.reviewhive.model.dao.entity.CategoryEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.dao.entity.QuestionaireUserEntity;
import com.reviewhive.model.dao.entity.RandomQuestionEntity;
import com.reviewhive.model.objects.QuestionObj;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Data
public class QuestionaireDto {
	
	private int id;

	public int questionaireCategoryId;
	
	@NotBlank(message= MessageConstant.CATEGORY_NAME_REQUIRED)
	public String questionaireAbbreviation;
	
	@NotBlank(message= MessageConstant.CATEGORY_STATUS_REQUIRED)
	private String questionaireName;
	
	@NotBlank(message= MessageConstant.CATEGORY_DESCRIPTION_REQUIRED)
	private String questionaireStatus;
	
	@NotBlank(message= MessageConstant.CATEGORY_DESCRIPTION_REQUIRED)
	private String questionaireDescription;
	
	@NotBlank(message= MessageConstant.CATEGORY_DESCRIPTION_REQUIRED)
	private String questionaireColor;
	
	private Boolean categoryModified = false;
	
	private List<CategoryEntity> categories;
	
	private List<QuestionaireDetailsEntity> questionaires;
	
	private QuestionaireEntity questionaire;
	
	private int page;
	
	private String updateType;
	
	private List<QuestionObj> questions;
	
	private List<QuestionDetailsEntity> retrievedQuestions;
	
	private Boolean showAnswerFlg;
	
	private Boolean showResultFlg;
	
	private Boolean answerRequiredFlg;
	
	private Boolean enablePreviousFlg; 
	
	private Boolean enableTimerFlg;
	
	private String hour;
	
	private String minute;
	
	private String second;
	
	private Boolean questionModified;
	
	private List<QuestionaireUserEntity> questionaireUser;
	
	private int examType;
	
	private RandomQuestionEntity randomQuestion;
	
}
