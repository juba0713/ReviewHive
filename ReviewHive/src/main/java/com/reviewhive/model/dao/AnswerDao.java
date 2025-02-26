package com.reviewhive.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewhive.model.dao.entity.AnswerEntity;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Service
@Repository
@Transactional
public interface AnswerDao extends JpaRepository<AnswerEntity, Integer> {
	
	/*
	 * Query for deleting a answer
	 */
	public static final String DELETE_ANSWER_BY_QUESTIONAIRE_ID = "DELETE FROM AnswerEntity a WHERE a.questionaireId = :questionaireId";
	
	/**
	 * Delete Answer By Questionaire Id
	 * @param questionaireId
	 */
	@Modifying
	@Query(value=DELETE_ANSWER_BY_QUESTIONAIRE_ID)
	public void deleteAnswerByQuestionaireId(@Param("questionaireId") int questionaireId);
	
	/*
	 * Query for deleting a answer by question id
	 */
	public static final String DELETE_ANSWER_BY_QUESTION_ID = "DELETE FROM AnswerEntity a WHERE a.questionId = :questionId";
	
	/**
	 * Delete Answer By Question Id
	 * @param questionId
	 */
	@Modifying
	@Query(value=DELETE_ANSWER_BY_QUESTION_ID)
	public void deleteAnswerByQuestionId(@Param("questionId") int questionId);
	
	/*
	 * Query for deleting a answer by id
	 */
	public static final String DELETE_ANSWER_BY_ID = "DELETE FROM AnswerEntity a WHERE a.id = :answerId";
	
	/**
	 * Delete Answer By Id
	 * @param questionId
	 */
	@Modifying
	@Query(value=DELETE_ANSWER_BY_ID)
	public void deleteAnswerById(@Param("answerId") int answerId);
	
	public static final String UPDATE_QUESTION_BY_ID = "UPDATE t_answer "
			+ "SET answer = :answer, "
			+ "is_correct = :isCorrect "
			+ "WHERE id = :answerId ";
	
	@Modifying
	@Query(value=UPDATE_QUESTION_BY_ID, nativeQuery=true)
	public void updateQuestionById(@Param("answer") String answer,
			@Param("isCorrect") Boolean isCorrect,
			@Param("answerId") int answerId);
	
	public static final String UPDATE_ANSWER_IMAGE = "UPDATE t_answer "
			+ "SET answer_image = :answerImage "
			+ "WHERE id = :answerId ";
	
	@Modifying
	@Query(value=UPDATE_ANSWER_IMAGE, nativeQuery=true)
	public void updateAnswerImage(@Param("answerImage") String answerImage,
			@Param("answerId") int answerId);
}
