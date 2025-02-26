package com.reviewhive.model.dao;

import java.sql.Timestamp;
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

import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.dao.entity.QuestionaireSettingsEntity;
import com.reviewhive.model.dao.entity.QuestionaireUserEntity;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
@Repository
@Transactional
public interface QuestionaireDao extends JpaRepository<QuestionaireEntity, Integer>{
	
	/*
	 * Query for Getting All Query
	 */
	public static final String GET_ALL_QUESTIONAIRE_BY_PAGE = "SELECT e.id,  "
			+ "e.category_id,  "
			+ "c.category_name,  "
			+ "e.questionaire_name,  "
			+ "e.abbreviation,  "
			+ "e.description,  "
			+ "e.is_open,  "
			+ "e.hex_color,  "
			+ "e.created_date,  "
			+ "e.updated_date,  "
			+ "CAST(CEIL(COUNT(*) OVER () / :limit) AS INTEGER) AS total_page,  "
			+ "(  "
			+ "	SELECT CAST(COUNT(a) AS INTEGER)  "
			+ "	FROM m_question a  "
			+ "	WHERE a.questionaire_id = e.id  "
			+ ") AS question_total  "
			+ "FROM m_questionaire e  "
			+ "JOIN m_category c ON c.id = e.category_id AND c.is_open = true AND c.delete_flg = false  "
			+ "WHERE e.delete_flg = false  "
			+ "LIMIT :limit OFFSET :offset  "
			+ "";	
	/**
	 * Get All Category Raw
	 * @return List<Object[]>
	 * @throws DataAccessException
	 */
	@Query(value=GET_ALL_QUESTIONAIRE_BY_PAGE, nativeQuery=true)
	public List<Object[]> getAllQuestionaireByPageRaw(
			@Param("limit") double limit,
			@Param("offset") int offset) throws DataAccessException;
	
	/**
	 * Get All Questonaire By Page
	 * @param limit
	 * @param offset
	 * @return List<QuestionaireDetailsEntity>
	 */
	default List<QuestionaireDetailsEntity> getAllQuestionaireByPage(int limit, int offset){
		List<Object[]> rawResults = getAllQuestionaireByPageRaw(limit, offset);
	    List<QuestionaireDetailsEntity> questionaires = new ArrayList<>();

	    for (Object[] objects : rawResults) {
	    	QuestionaireDetailsEntity questionaire = new QuestionaireDetailsEntity(objects);  
	    	questionaires.add(questionaire);
	    }

	    return questionaires;
	}
	
	/*
	 * Query for getting a single questionaire by its id
	 */
	public static final String GET_QUESTIONAIRE_BY_ID = "SELECT e "
			+ "FROM QuestionaireEntity e "
			+ "WHERE e.id = :id "
			+ "AND e.deleteFlg = false ";
	
	/**
	 * Get questionaire by its id
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Query(value=GET_QUESTIONAIRE_BY_ID)
	public QuestionaireEntity getQuestionaireById(int id) throws DataAccessException;
	
	/*
	 * Query for updating Questionaire status
	 */
	public static final String UPDATE_QUESTIONAIRE_STATUS = "UPDATE m_questionaire "
			+ "SET is_open = :status "
			+ "WHERE id = :id ";
	
	/**
	 * Update Questionaire Status
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_QUESTIONAIRE_STATUS, nativeQuery=true)
	public void updateQuestionaireStatus(
			@Param("status") boolean status, 
			@Param("id") int id);
	
	/*
	 * Query for updating Questionaire all
	 */
	public static final String UPDATE_QUESTIONAIRE = "UPDATE m_questionaire "
			+ "SET questionaire_name = :questionaireName, "
			+ "description = :questionaireDescription, "
			+ "abbreviation = :questionaireAbbreviation, "
			+ "category_id = :questionaireCategoryId, "
			+ "hex_color = :questionaireColor, "
			+ "is_open = :questionaireStatus, "
			+ "updated_date = :updateDate "
			+ "WHERE id = :id ";
	
	/**
	 * Update Questionaire all
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_QUESTIONAIRE, nativeQuery=true)
	public void updateQuestionaireAll(
			@Param("questionaireName") String questionaireName, 
			@Param("questionaireDescription") String questionaireDescription,
			@Param("questionaireAbbreviation") String questionaireAbbreviation,
			@Param("questionaireCategoryId") int questionaireCategoryId,
			@Param("questionaireColor") String questionaireColor,
			@Param("questionaireStatus") boolean questionaireStatus,
			@Param("updateDate") Timestamp updateDate,
			@Param("id") int id);
	
	/*
	 * Query for updating Questionaire delete
	 */
	public static final String UPDATE_QUESTIONAIRE_DELETE = "UPDATE m_questionaire "
			+ "SET delete_flg = true "
			+ "WHERE id = :id ";
	
	/**
	 * Update Questionaire Delete
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_QUESTIONAIRE_DELETE, nativeQuery=true)
	public void updateQuestionaireDelete(@Param("id") int id);
	
	/*
	 * Query for updating Questionaire settings
	 */
	public static final String UPDATE_QUESTIONAIRE_SETTINGS = "UPDATE m_questionaire "
			+ "SET show_answer_flg = :showAnswerFlg, "
			+ "show_result_flg = :showResultFlg, "
			+ "answer_required_flg = :answerRequiredFlg, "
			+ "enable_previous_flg = :enablePreviousFlg, "
			+ "enable_timer_flg = :enableTimerFlg, "
			+ "hour = :hour, "
			+ "minute = :minute, "
			+ "second = :second, "
			+ "updated_date = :updateDate "
			+ "WHERE id = :id ";
	
	/**
	 * Update Questionaire all
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_QUESTIONAIRE_SETTINGS, nativeQuery=true)
	public void updateQuestionaireSettings(
			@Param("showAnswerFlg") Boolean showAnswerFlg,
			@Param("showResultFlg") Boolean showResultFlg,
			@Param("answerRequiredFlg") Boolean answerRequiredFlg,
			@Param("enablePreviousFlg") Boolean enablePreviousFlg,
			@Param("enableTimerFlg") Boolean enableTimerFlg,
			@Param("hour") String hour,
			@Param("minute") String minute,
			@Param("second") String second,
			@Param("updateDate") Timestamp updateDate,
			@Param("id") int id);
	
	/*
	 * Query for Getting All Query
	 */
	public static final String GET_QUESTIONAIRE_SETTINGS = "SELECT a.show_answer_flg, "
			+ "a.show_result_flg, "
			+ "a.answer_required_flg, "
			+ "a.enable_previous_flg, "
			+ "a.enable_timer_flg, "
			+ "a.hour, "
			+ "a.minute, "
			+ "a.second "
			+ "FROM m_questionaire a "
			+ "WHERE a.id = :id "
			+ "AND a.is_open = true "
			+ "AND a.delete_flg = false ";	
	/**
	 * Get All Category Raw
	 * @return List<Object[]>
	 * @throws DataAccessException
	 */
	@Query(value=GET_QUESTIONAIRE_SETTINGS, nativeQuery=true)
	public List<Object[]> getQuestonaireSettingsRaw(
			@Param("id") int id) throws DataAccessException;
	
	/**
	 * Get All Questonaire By Page
	 * @param limit
	 * @param offset
	 * @return List<QuestionaireDetailsEntity>
	 */
	default QuestionaireSettingsEntity getQuestonaireSettings(int id){
		List<Object[]> rawResults = getQuestonaireSettingsRaw(id);

	    QuestionaireSettingsEntity questionaireSettings = new QuestionaireSettingsEntity(rawResults.get(0));  
	    	
	    return questionaireSettings;
	}
	
	/*
	 * Query for getting all the questionaire for user
	 */
	public final String GET_ALL_QUESTIONAIRE_FOR_USER = "SELECT q.id, "
			+ "q.abbreviation, "
			+ "q.questionaire_name, "
			+ "q.hex_color, "
			+ "q.description "
			+ "FROM m_questionaire q "
			+ "WHERE q.delete_flg = false ";
	
	/**
	 * Get Questionaire For User Raw
	 * @return List<Object[]>
	 * @throws DataAccessException
	 */
	@Query(value=GET_ALL_QUESTIONAIRE_FOR_USER, nativeQuery=true)
	public List<Object[]> getAllQuestionaireForUserRaw() throws DataAccessException;
	
	/**
	 * Get Questionaire For User
	 * @return List<QuestionaireUserEntity>
	 */
	default List<QuestionaireUserEntity> getAllQuestionaireForUser(){
		
		List<Object[]> rawResults = getAllQuestionaireForUserRaw();
	    List<QuestionaireUserEntity> questionaires = new ArrayList<>();
	    
	    for (Object[] objects : rawResults) {
	    	QuestionaireUserEntity questionaire = new QuestionaireUserEntity(objects);  
	    	questionaires.add(questionaire);
	    }

	    return questionaires;
	}
}
