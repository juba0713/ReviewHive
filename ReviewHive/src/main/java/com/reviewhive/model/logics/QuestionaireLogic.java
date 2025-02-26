package com.reviewhive.model.logics;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.reviewhive.model.dao.entity.AnswerEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;
import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.dao.entity.QuestionaireSettingsEntity;
import com.reviewhive.model.dao.entity.QuestionaireUserEntity;
import com.reviewhive.model.dao.entity.RandomQuestionEntity;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
public interface QuestionaireLogic {
	
	/**
	 * Save Questionaire
	 * @param entity
	 */
	public void saveQuestionaire(QuestionaireEntity entity);
	
	/**
	 * Get All Questionaire
	 * @param page
	 */
	public List<QuestionaireDetailsEntity> getAllQuestionaireByPage(int page);
	
	/**
	 * Get Questionaire by its id
	 * @param id
	 * @return QuestionaireEntity
	 */
	public QuestionaireEntity getQuestionaireById(int id);
	
	/**
	 * Update Questionaire Status
	 * @param status
	 * @param id
	 */
	public void updateQuestionaireStatus(boolean status, int id);
	
	/**
	 * Update Questionaire All
	 * @param questionaireName
	 * @param questionaireDescription
	 * @param questionaireAbbreviation
	 * @param questionaireCategoryId
	 * @param questionaireColor
	 * @param questionaireStatus
	 * @param updateDate
	 * @param id
	 */
	public void updateQuestionaireAll(String questionaireName, 
			String questionaireDescription,
			String questionaireAbbreviation,
			int questionaireCategoryId,
			String questionaireColor,
			boolean questionaireStatus,
			Timestamp updateDate,
			int id);
	
	/**
	 * Update Questionaire Delete
	 * @param status
	 * @param id
	 */
	public void updateQuestionaireDelete(int id);
	
	/**
	 * Save All Questionaire Questions
	 * @param entites
	 */
	public void saveAllQuestionaireQuestion(List<QuestionEntity> entites);
	
	/**
	 * Save Questionaire Question
	 * @param entity
	 */
	public int saveQuestionaireQuestion(QuestionEntity entity);
	
	/**
	 * Save All Questionaire Question Answers
	 * @param entites
	 */
	public void saveAllQuestionaireQuestionAnswers(List<AnswerEntity> entites);
	
	/**
	 * Save Questionaire Question Answer
	 * @param entity
	 */
	public void saveQuestionaireQuestionAnswer(AnswerEntity entity);
	
	/**
	 * Get Questionaire Questions & Answers by Questionaire Id
	 * @param questionaireId
	 * @return
	 */
	public List<QuestionDetailsEntity> getQuestionaireQuestions(int questionaireId);
	
	/**
	 * Delete All Questions by Questonaire Id
	 * @param questionaireId
	 */
	public void deleteQuestionsByQuestionaireId(int questionaireId);
	
	/**
	 * Delete All Answers by Questionaire Id
	 * @param questionaireId
	 */
	public void deleteAnswersByQuestionaireId(int questionaireId);
	
	/**
	 * Delete Question By Id
	 * @param questionId
	 */
	public void deleteQuestionbyId(int questionId);
	
	/**
	 * Delete All Answers By Question Id
	 * @param questionId
	 */
	public void deleteAnswersByQuestionId(int questionId);
	
	/**
	 * Delete Answer By Id
	 * @param answerId
	 */
	public void deleteAnswerById(int answerId);
	
	/**
	 * Update Question Details By Id
	 * @param question
	 * @param questionImage
	 * @param questionId
	 */
	public void updateQuestionById(String question, int questionId);
	
	/**
	 * Update Answer By Id
	 * @param answer
	 * @param isCorrect
	 * @param answerId
	 */
	public void updateAnswerById(String answer, Boolean isCorrect, int answerId);
	
	/**
	 * Update Question Image By Id
	 * @param questionImage
	 * @param questionId
	 */
	public void updateQuestionImageById(String questionImage, int questionId);
	
	/**
	 * Update Questionaire Settings
	 * @param showAnswerFlg
	 * @param showResultFlg
	 * @param answerRequiredFlg
	 * @param enablePreviousFlg
	 * @param enableTimerFlg
	 * @param hour
	 * @param minute
	 * @param second
	 * @param updateDate
	 * @param id
	 */
	public void updateQuestionaireSettings(Boolean showAnswerFlg,
			Boolean showResultFlg,
			Boolean answerRequiredFlg,
			Boolean enablePreviousFlg,
			Boolean enableTimerFlg,
			String hour,
			String minute,
			String second,
			Timestamp updateDate,
			int id);
	
	/**
	 * Get Questionaire Settings
	 * @param id
	 * @return QuestionaireSettingsEntity
	 */
	public QuestionaireSettingsEntity getQuestionaireSettings(int id);
	
	/**
	 * Update Answer Image By Answer Id
	 * @param answerImage
	 * @param answerId
	 */
	public void updateAnswerImageByAnswerId(String answerImage, int answerId);
	
	/**
	 * To get all the questionaire for user
	 * @return List<QuestionaireUserEntity>
	 */
	public List<QuestionaireUserEntity> getAllQuestionaireForUser();
	
	/**
	 * Get a random Question by questionaire id
	 * @param questionaireId
	 * @return RandomQuestionEntity
	 */
	public RandomQuestionEntity getRandomQuestionByQuestionaireId(int questionaireId);
	
}
