package com.reviewhive.model.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.util.ApplicationPropertiesRead;
import com.reviewhive.common.util.DateFormatUtil;
import com.reviewhive.model.dao.entity.AnswerEntity;
import com.reviewhive.model.dao.entity.QuestionDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionEntity;
import com.reviewhive.model.dao.entity.QuestionaireDetailsEntity;
import com.reviewhive.model.dao.entity.QuestionaireEntity;
import com.reviewhive.model.dao.entity.QuestionaireSettingsEntity;
import com.reviewhive.model.dao.entity.QuestionaireUserEntity;
import com.reviewhive.model.dao.entity.RandomQuestionEntity;
import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.logics.QuestionaireLogic;
import com.reviewhive.model.objects.AnswerObj;
import com.reviewhive.model.objects.QuestionObj;
import com.reviewhive.model.services.QuestionaireService;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Service
public class QuestionaireServiceImpl implements QuestionaireService {
	
	@Autowired
	private QuestionaireLogic questionaireLogic;

	/**
	 * Save Questionaire
	 * @param inDto
	 */
	@Override
	public void saveQuestionaire(QuestionaireDto inDto) {
		
		QuestionaireEntity questionaire = new QuestionaireEntity();
		questionaire.setCategoryId(inDto.getQuestionaireCategoryId());
		questionaire.setAbbreviation(inDto.getQuestionaireAbbreviation());
		questionaire.setQuestionaireName(inDto.getQuestionaireName());
		questionaire.setDescription(inDto.getQuestionaireDescription());
		questionaire.setHexColor(inDto.getQuestionaireColor());
		questionaire.setIsOpen(inDto.getQuestionaireStatus().equals("true") ? true : false);
		questionaire.setCreatedDate(DateFormatUtil.currentDate());
		questionaire.setUpdatedDate(DateFormatUtil.currentDate());
		questionaire.setDeleteFlg(false);
		questionaire.setShowAnswerFlg(false);
		questionaire.setShowResultFlg(false);
		questionaire.setAnswerRequiredFlg(false);
		questionaire.setEnablePreviousFlg(false);
		questionaire.setEnableTimerFlg(false);
		
		questionaireLogic.saveQuestionaire(questionaire);
	}

	/**
	 * Get All Questionaire By Page
	 * @param inDto
	 * @return QuestionaireDto
	 */
	@Override
	public QuestionaireDto getAllQuestionaireByPage(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		List<QuestionaireDetailsEntity> questionaires = questionaireLogic.getAllQuestionaireByPage(inDto.getPage());
		
		outDto.setQuestionaires(questionaires);
		
		return outDto;
	}

	/**
	 * Get Questionaire by its id
	 * @param inDto
	 * @return
	 */
	@Override
	public QuestionaireDto getQuestionaireById(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		QuestionaireEntity questionaire = questionaireLogic.getQuestionaireById(inDto.getId());
		
		outDto.setQuestionaire(questionaire);		
		
		return outDto;
	}

	/**
	 * Update Questionaire
	 * @param inDto
	 */
	@Override
	public void updateQuestionaire(QuestionaireDto inDto) {
		
		Boolean status = false;
		if(inDto.getUpdateType().equals(CommonConstant.UPDATE_STATUS) ||
			inDto.getUpdateType().equals(CommonConstant.UPDATE_ALL)){
			status = inDto.getQuestionaireStatus().equals("true") ? true : false;
		}
		
		if(inDto.getCategoryModified()) {
			questionaireLogic.deleteQuestionsByQuestionaireId(inDto.getId());
			questionaireLogic.deleteAnswersByQuestionaireId(inDto.getId());
		}
			
		switch(inDto.getUpdateType()) {
			
			case CommonConstant.UPDATE_STATUS:
				
				questionaireLogic.updateQuestionaireStatus(status, inDto.getId());
				break;
			case CommonConstant.UPDATE_ALL:
				
				questionaireLogic.updateQuestionaireAll(inDto.getQuestionaireName(), 
						inDto.getQuestionaireDescription(), 
						inDto.getQuestionaireAbbreviation(),
						inDto.getQuestionaireCategoryId(), 
						inDto.getQuestionaireColor(), 
						status, 
						DateFormatUtil.currentDate(), 
						inDto.getId());
				
				break;
			case CommonConstant.UPDATE_DELETE:
				
				questionaireLogic.updateQuestionaireDelete(inDto.getId());
				
				break;
		}
		
	}

	/**
	 * To save the questionaire question
	 * @param inDto
	 * @throws IOException 
	 */
	@Override
	public void saveQuestionaireQuestion(QuestionaireDto inDto) throws IOException {
	    if (inDto.getQuestions().isEmpty()) return;

	    Timestamp currentDate = DateFormatUtil.currentDate();
	    
	    questionaireLogic.updateQuestionaireSettings(inDto.getShowAnswerFlg() != null ? true : false, 
	    		inDto.getShowResultFlg() != null ? true : false, 
	    		inDto.getAnswerRequiredFlg() != null ? true : false, 
	    		inDto.getEnablePreviousFlg() != null ? true : false, 
	    		inDto.getEnableTimerFlg() != null ? true : false, 
	    		inDto.getHour(), 
	    		inDto.getMinute(),
	    		inDto.getSecond(), 
	    		currentDate, 
	    		inDto.getId());
	    	    
	    for (QuestionObj questionObj : inDto.getQuestions()) {
	        boolean isExisting = questionObj.getQuestionId() != 0;
	        
	        if (isExisting) {
	            handleExistingQuestion(questionObj, inDto);
	        } else {
	            handleNewQuestion(questionObj, inDto, currentDate);
	        }
	    }
	}

	private void handleExistingQuestion(QuestionObj questionObj, QuestionaireDto inDto) throws IOException {
	    if (questionObj.getHasDeleted()) {
	        questionaireLogic.deleteQuestionbyId(questionObj.getQuestionId());
	        questionaireLogic.deleteAnswersByQuestionId(questionObj.getQuestionId());
	        return;
	    }

	    if (questionObj.getHasQuestionImageModified()) {
	        questionaireLogic.updateQuestionImageById(
	            questionObj.getQuestionImage().getOriginalFilename(),
	            questionObj.getQuestionId()
	        );
	        saveQuestionImage(questionObj.getQuestionImage(), inDto.getId(), questionObj.getQuestionId());
	    }

	    if (questionObj.getHasModified()) {
	        questionaireLogic.updateQuestionById(
	            questionObj.getQuestion(),
	            questionObj.getQuestionId()
	        );
	        saveAnswers(questionObj.getAnswers(), inDto.getId(), questionObj.getQuestionId());
	        saveQuestionImage(questionObj.getQuestionImage(), inDto.getId(), questionObj.getQuestionId());
	    }
	}

	private void handleNewQuestion(QuestionObj questionObj, QuestionaireDto inDto, Timestamp currentDate) throws IOException {
	    QuestionEntity question = new QuestionEntity();
	    question.setQuestionaireId(inDto.getId());
	    question.setQuestionType(questionObj.getQuestionType());
	    question.setQuestion(questionObj.getQuestion());
	    question.setIsOpen(true);
	    question.setCreatedDate(currentDate);
	    question.setUpdatedDate(currentDate);
	    question.setQuestionImage(questionObj.getQuestionImage().getOriginalFilename());
	    question.setDeleteFlg(false);

	    int questionId = questionaireLogic.saveQuestionaireQuestion(question);

	    saveQuestionImage(questionObj.getQuestionImage(), inDto.getId(), questionId);
	    saveAnswers(questionObj.getAnswers(), inDto.getId(), questionId);
	}

	private void saveAnswers(List<AnswerObj> questionAnswers, int questionaireId, int questionId) throws IOException {
		
		List<AnswerEntity> answers = new ArrayList<>();
		
		if(questionAnswers != null && questionAnswers.size() != 0) {
			int count = 0;
			for(AnswerObj answerObj : questionAnswers) {
				
				count++;
				
				String originalFilename = answerObj.getAnswerImage().getOriginalFilename();
		        String fileExtension = "";

		        if (originalFilename != null && originalFilename.contains(".")) {
		            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
		        }

		        // Define the filename with the extension
		        String newFilename = "answer-image" + "-" + count + fileExtension;

				//If deleted from the questions but already in the database
				if(answerObj.getHasDeleted() && answerObj.getAnswerId() != 0) {
					
					questionaireLogic.deleteAnswerById(answerObj.getAnswerId());
				
					continue;
				}
				if(answerObj.getHasAnswerImageModified() && answerObj.getAnswerId() != 0) {
					questionaireLogic.updateAnswerImageByAnswerId(
							newFilename,
				            answerObj.getAnswerId()
				        );
				        saveAnswerImage(answerObj.getAnswerImage(), questionaireId, questionId, newFilename);
				}
				
				//If already in the database and is not modified or deleted from the questions but is not in the database
				if(answerObj.getAnswerId() != 0 && !answerObj.getHasModified() || answerObj.getHasDeleted()) {
					continue;
				} 

				//If modified and already in the database
				if(answerObj.getHasModified() && answerObj.getAnswerId() != 0) {
					
					questionaireLogic.updateAnswerById(answerObj.getAnswer(), answerObj.getIsCorrect(), answerObj.getAnswerId());
					
					continue;
				}
			
				
				AnswerEntity answer = new AnswerEntity();
				answer.setQuestionId(questionId);
				answer.setQuestionaireId(questionaireId);
				answer.setAnswer(answerObj.getAnswer());
				answer.setIsCorrect(answerObj.getIsCorrect());
				answer.setAnswerImage(newFilename);
				answer.setIsOpen(true);							
				answer.setCreatedDate(DateFormatUtil.currentDate());
				answer.setUpdatedDate(DateFormatUtil.currentDate());
				answer.setDeleteFlg(false);
				
				answers.add(answer);
				saveAnswerImage(answerObj.getAnswerImage(), questionaireId, questionId, newFilename);
			}
			
			questionaireLogic.saveAllQuestionaireQuestionAnswers(answers);
		}
	}

	private void saveQuestionImage(MultipartFile questionImage, int questionaireId, int questionId) throws IOException {
		
		Path uploadPath = Paths.get(ApplicationPropertiesRead.getProperty("question.image.path"));

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
	
        if (questionImage != null && !questionImage.isEmpty()) {
   
            Path questionFolderPath = uploadPath.resolve(String.valueOf(questionId));

            // Recreate the folder
            Files.createDirectories(questionFolderPath);
            
            // Get the original filename and extract the extension
            String originalFilename = questionImage.getOriginalFilename();

            // Define the file path within the subfolder
            Path filePath = questionFolderPath.resolve(originalFilename);

            // Save the image
            Files.copy(questionImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
	}
	
	private void saveAnswerImage(MultipartFile answerImage, int questionaireId, int questionId, String answerImageName) throws IOException {
	    // Construct the folder path based on the question ID
	    Path questionFolderPath = Paths.get(ApplicationPropertiesRead.getProperty("question.image.path"), String.valueOf(questionId));

	    // Ensure the directory exists; if not, create it
	    if (!Files.exists(questionFolderPath)) {
	        Files.createDirectories(questionFolderPath);
	    }

	    if (answerImage != null && !answerImage.isEmpty()) {

	        // Construct the full file path
	        Path filePath = questionFolderPath.resolve(answerImageName);

	        // Save the image
	        Files.copy(answerImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	    }
	}


	/**
	 * Get Questionaire Questions & Answers
	 * @param inDto
	 * @return
	 */
	@Override
	public QuestionaireDto getQuestionaireQuestions(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		List<QuestionDetailsEntity> retrievedQuestions = questionaireLogic.getQuestionaireQuestions(inDto.getId());
		
		QuestionaireSettingsEntity settings = questionaireLogic.getQuestionaireSettings(inDto.getId());
		
		outDto.setShowAnswerFlg(settings.getShowAnswerFlg());
		
		outDto.setShowResultFlg(settings.getShowResultFlg());
		
		outDto.setAnswerRequiredFlg(settings.getAnswerRequiredFlg());
		
		outDto.setEnablePreviousFlg(settings.getEnablePreviousFlg());
		
		outDto.setEnableTimerFlg(settings.getEnableTimerFlg());
		
		outDto.setHour(settings.getHour());
		
		outDto.setMinute(settings.getMinute());
		
		outDto.setSecond(settings.getSecond());
		
		outDto.setRetrievedQuestions(retrievedQuestions);
		
		return outDto;
	}
	
	/**
	 * To get all the questionaire for user
	 * @return QuestionaireDto
	 */
	@Override
	public QuestionaireDto getQuestionaireForUser() {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		List<QuestionaireUserEntity> questionaires = questionaireLogic.getAllQuestionaireForUser();
		
		outDto.setQuestionaireUser(questionaires);
		
		return outDto;
	}

	/**
	 * Get a random Question 
	 * @param inDto
	 * @return QuestionaireDto
	 */
	@Override
	public QuestionaireDto getRandomQuestion(QuestionaireDto inDto) {
		
		QuestionaireDto outDto = new QuestionaireDto();
		
		RandomQuestionEntity question = questionaireLogic.getRandomQuestionByQuestionaireId(inDto.getId());
		
		outDto.setRandomQuestion(question);
		
		return outDto;
	}

}
