package com.reviewhive.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.services.QuestionaireService;

@RestController
@RequestMapping("/admin/api/questionaire")
public class AdminQuestionaireAPI {

	@Autowired
	private QuestionaireService questionaireService;
	
	/**
	 * Get All Questionaire By Page
	 * @param page
	 * @return
	 */
	@GetMapping("/get-all")
	public ResponseEntity<QuestionaireDto> getAllCategory(@RequestParam("page") int page){
		
		QuestionaireDto inDto = new QuestionaireDto();
		
		inDto.setPage(page);
		
		QuestionaireDto outDto = questionaireService.getAllQuestionaireByPage(inDto);
		
		return ResponseEntity.ok(outDto);
	}
	
	/**
	 * Update Questionaire Status
	 * @param page
	 * @return
	 */
	@PostMapping("/update-status")
	public ResponseEntity<Void> updateQuestionaireStatus(
			@RequestParam("id") int id,
			@RequestParam("status") String status){
		
		QuestionaireDto inDto = new QuestionaireDto();
		
		inDto.setQuestionaireStatus(status);
		
		inDto.setId(id);
		
		inDto.setUpdateType(CommonConstant.UPDATE_STATUS);
		
		questionaireService.updateQuestionaire(inDto);
		
		return ResponseEntity.ok().build();
	}
}
