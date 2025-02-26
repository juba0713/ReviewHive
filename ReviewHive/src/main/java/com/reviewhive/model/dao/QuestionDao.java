package com.reviewhive.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewhive.model.dao.entity.AnswerDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;
import com.reviewhive.model.dao.entity.RandomQuestionEntity;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Service
@Repository
@Transactional
public interface QuestionDao extends JpaRepository<QuestionEntity, Integer>{
	
	/*
	 * Query for Getting Questions Details By Id
	 */
	public static final String GET_QUESTION_DETAILS_BY_ID = "SELECT q.id,\r\n"
			+ "	q.questionaire_id,\r\n"
			+ "	q.question,\r\n"
			+ "	q.question_type,"
			+ " q.question_image \r\n"
			+ "FROM m_question q\r\n"
			+ "WHERE q.questionaire_id = :questionaireId\r\n"
			+ "AND q.is_open = true\r\n"
			+ "AND q.delete_flg = false "
			+ "ORDER BY q.id ASC ";
	
	public static final String GET_QUESTION_ANSWERS_BY_QUESTION_ID = "SELECT a.id,\r\n"
			+ "	a.question_id,\r\n"
			+ "	a.answer,\r\n"
			+ "	a.is_correct,"
			+ " a.answer_image\r\n"
			+ "FROM t_answer a\r\n"
			+ "WHERE a.question_id = :questionId\r\n"
			+ "AND a.is_open = true\r\n"
			+ "AND a.delete_flg = false "
			+ "ORDER BY a.id ASC";
	
	/**
	 * Get Questions By Questionaire Id
	 * @param questionaireId
	 * @return
	 * @throws DataAccessException
	 */
	@Query(value=GET_QUESTION_DETAILS_BY_ID, nativeQuery=true)
	public List<Object[]> getQuestionDetailsByQuestionaireIdRaw(int questionaireId) throws DataAccessException;
	
	/**
	 * Get Answer By Question Id
	 * @param questionId
	 * @return
	 * @throws DataAccessException
	 */
	@Query(value=GET_QUESTION_ANSWERS_BY_QUESTION_ID, nativeQuery=true)
	public List<Object[]> getAnswerDetailsByQuestionIdRaw(int questionId) throws DataAccessException;
	
	/**
	 * Get Question Details along with the answers
	 * @param questionaireId
	 * @return
	 */
	default List<QuestionDetailsEntity> getQuestionDetailsByQuestionaireId(int questionaireId){
		
		List<QuestionDetailsEntity> questions = new ArrayList<>();
		
		List<Object[]> questionsRaw = getQuestionDetailsByQuestionaireIdRaw(questionaireId);
		
		for(Object[] qObject : questionsRaw) {
			
			List<AnswerDetailsEntity> answers = new ArrayList<>();
			
			QuestionDetailsEntity question = new QuestionDetailsEntity(qObject);
			
			List<Object[]> answersRaw = getAnswerDetailsByQuestionIdRaw(question.getId());
			
			for(Object[] aObject : answersRaw) {
				
				AnswerDetailsEntity answer = new AnswerDetailsEntity(aObject);
				
				answers.add(answer);
			}
			
			question.setAnswers(answers);
			
			questions.add(question);
		}
		
		return questions;
	}
	
	/*
	 * Query for deleting a question
	 */
	public static final String DELETE_QUESTION_QUESTIONAIRE_ID = "DELETE FROM QuestionEntity q WHERE q.questionaireId = :questionaireId";
	
	/**
	 * Delete Questions By Questionaire Id
	 * @param questionaireId
	 */
	@Modifying
	@Query(value=DELETE_QUESTION_QUESTIONAIRE_ID)
	public void deleteQuestionByQuestionaireId(int questionaireId);
	
	/*
	 * Query for deleting a question
	 */
	public static final String DELETE_QUESTION_BY_ID = "DELETE FROM QuestionEntity q WHERE q.id = :questionId";
	
	/**
	 * Delete Questions By Id
	 * @param questionId
	 */
	@Modifying
	@Query(value=DELETE_QUESTION_BY_ID)
	public void deleteQuestionById(int questionId);
	
	public static final String UPDATE_QUESTION_BY_ID = "UPDATE m_question "
			+ "SET question = :question "
			+ "WHERE id = :questionId ";
	
	@Modifying
	@Query(value=UPDATE_QUESTION_BY_ID, nativeQuery=true)
	public void updateQuestionById(@Param("question") String question,
			@Param("questionId") int questionId);
	
	public static final String UPDATE_QUESTION_IMAGE_BY_ID = "UPDATE m_question "
			+ "SET question_image = :questionImage "
			+ "WHERE id = :questionId ";
	
	@Modifying
	@Query(value=UPDATE_QUESTION_IMAGE_BY_ID, nativeQuery=true)
	public void updateQuestionImageById(@Param("questionImage") String questionImage,
			@Param("questionId") int questionId);
	
	/*
	 * Query for retrieving a random question using questionaire id
	 */
	public static final String GET_RANDOM_QUESTION_BY_QUESTIONAIRE_ID = "SELECT q.id,\r\n"
			+ "q.question,\r\n"
			+ "q.question_image\r\n"
			+ "FROM m_question q\r\n"
			+ "WHERE q.questionaire_id = 1\r\n"
			+ "ORDER BY RANDOM()\r\n"
			+ "LIMIT 1;\r\n";
	
	@Query(value=GET_RANDOM_QUESTION_BY_QUESTIONAIRE_ID, nativeQuery=true)
	public List<Object[]> getRandomQUestionByQuestionaireIdRaw(int questionId) throws DataAccessException;
	
	/**
	 * Get Question Details along with the answers
	 * @param questionaireId
	 * @return
	 */
	default RandomQuestionEntity getRandomQUestionByQuestionaireId(int questionaireId){
		
		List<Object[]> rawData = getRandomQUestionByQuestionaireIdRaw(questionaireId);
		
		if(rawData.size() == 0) {
			return null;
		}
		
		RandomQuestionEntity question = new RandomQuestionEntity(rawData.get(0));
		
		List<AnswerDetailsEntity> answers = new ArrayList<>();
		
		List<Object[]> answersRaw = getAnswerDetailsByQuestionIdRaw(question.getId());
		
		for(Object[] aObject : answersRaw) {
			
			AnswerDetailsEntity answer = new AnswerDetailsEntity(aObject);
			
			answers.add(answer);
		}
		
		question.setAnswers(answers);
		
		return question;
	}
	
}
