package com.reviewhive.controllers.admin;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.constant.MessageConstant;
import com.reviewhive.model.dto.QuestionaireDto;
import com.reviewhive.model.objects.AnswerObj;
import com.reviewhive.model.objects.QuestionObj;
import com.reviewhive.model.services.CategoryService;
import com.reviewhive.model.services.QuestionaireService;

import jakarta.validation.Valid;

/**
 * @author Julius P. Basas
 * @added 12/19/2024
 */
@Controller
@RequestMapping("/admin")
public class AdminQuestionaireController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private QuestionaireService questionaireService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
        binder.setAutoGrowCollectionLimit(10000); // Set to a value larger than your maximum index
    }

	/**
	 * Show Category List Screen
	 * @return String
	 */
	@GetMapping("/questionaire-list")
	public String showQuestionairesScreen(
			Model model,
			@ModelAttribute QuestionaireDto webDto) {
		
		return "admin/questionaire-list";
	}
	
	/**
	 * Show Category Add Screen
	 * @return String
	 */
	@GetMapping("/questionaire-add")
	public String showQuestionaireAddScreen(@ModelAttribute QuestionaireDto webDto) {
		
		webDto.setCategories(categoryService.getAllCategory().getCategoriesSelection());
		
		return "admin/questionaire-add";
	}
	

	/**
	 * Post Questionaire Add
	 * @param model
	 * @param webDto
	 * @param result
	 * @param ra
	 * @return
	 */
	@PostMapping("/questionaire-add")
	public String postQuestionaireAddScreen(
			Model model,
			@Valid @ModelAttribute QuestionaireDto webDto,
			BindingResult result,
			RedirectAttributes ra) {
				
		if(result.hasErrors()) {
			
			return "admin/questionaire-add";
		}
		
		questionaireService.saveQuestionaire(webDto);
		
		String successMessage = webDto.getQuestionaireName() + MessageConstant.QUESTIONAIRE_ADDED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/questionaire-list";
	}
	
	/**
	 * Show Questionaire Edit Screen
	 * @return String
	 */
	@GetMapping("/questionaire-edit")
	public String showQuestionaireEditScreen(			
			@ModelAttribute QuestionaireDto webDto) {
		
		webDto.setCategories(categoryService.getAllCategory().getCategoriesSelection());
		webDto.setQuestionaire(questionaireService.getQuestionaireById(webDto).getQuestionaire());
		
		return "admin/questionaire-edit";
	}
	
	/**
	 * Post Questionaire Edit
	 * @param webDto
	 * @param ra
	 * @param result
	 * @return String
	 */
	@PostMapping("/questionaire-edit")
	public String postQuestionaireEditScreen(
			Model model,
			@ModelAttribute @Valid QuestionaireDto webDto,
			RedirectAttributes ra,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "admin/category-edit";
		}
		
		webDto.setUpdateType(CommonConstant.UPDATE_ALL);
		
		questionaireService.updateQuestionaire(webDto);
		
		String successMessage = webDto.getQuestionaireName() + MessageConstant.QUESTIONAIRE_EDITED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/questionaire-list";
	}
	
	/**
	 * Questionaire Delete
	 * @param webDto
	 * @param ra
	 * @return
	 */
	@PostMapping("/questionaire-delete")
	public String postCategoryDeleteScreen(
			@ModelAttribute QuestionaireDto webDto,
			RedirectAttributes ra) {
		
		webDto.setUpdateType(CommonConstant.UPDATE_DELETE);
		
		questionaireService.updateQuestionaire(webDto);
		
		String successMessage = MessageConstant.QUESTIONAIRE_DELETED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/questionaire-list";
	}
	
	/**
	 * Show Questionaire Questions Screen
	 * @param webDto
	 * @return
	 */
	@GetMapping("/questionaire/questions")
	public String showQuestionaireQuestionScreen(@ModelAttribute QuestionaireDto webDto) {
		
		webDto.setQuestionaire(questionaireService.getQuestionaireById(webDto).getQuestionaire());
		
		QuestionaireDto outDto = questionaireService.getQuestionaireQuestions(webDto);
		
		webDto.setRetrievedQuestions(outDto.getRetrievedQuestions());
		
		webDto.setShowAnswerFlg(outDto.getShowAnswerFlg());
		
		webDto.setShowResultFlg(outDto.getShowResultFlg());
		
		webDto.setAnswerRequiredFlg(outDto.getAnswerRequiredFlg());
		
		webDto.setEnablePreviousFlg(outDto.getEnablePreviousFlg());
		
		webDto.setEnableTimerFlg(outDto.getEnableTimerFlg());
		
		webDto.setHour(outDto.getHour());
		
		webDto.setMinute(outDto.getMinute());
		
		webDto.setSecond(outDto.getSecond());
		
		return "admin/questionaire-question";
	}
	 
	@PostMapping("test")
	public String Test(QuestionaireDto webDto) throws IOException, Exception {
		 
		
	
//		for(QuestionObj question : webDto.getQuestions()) {
//			System.out.println("Question Type: " + question.getQuestionType());
//			System.out.println("Question: " + question.getQuestion());
//			System.out.println("Question Id: " + question.getQuestionId());
//			System.out.println("Question Modified: " + question.getHasModified());
//			System.out.println("Question Deleted: " + question.getHasDeleted());
//			
//			if(question.getAnswers() != null) {
//				for(AnswerObj answer : question.getAnswers()) {
//					System.out.println("Answer: " + answer.getAnswer());
//					System.out.println("Answer Image" + answer.getAnswerImage().getOriginalFilename());
//					System.out.println("Answer Id: " + answer.getAnswerId());
//					System.out.println("Answer Modified: " + answer.getHasModified());
//					System.out.println("Answer Deleted: " + answer.getHasDeleted());
//				}
//				System.out.println("Answer Count" + question.getAnswers().size());
//			}
//			
//			//System.out.println(question.getQuestionImage().getOriginalFilename());
//			System.out.println("----------------------------------------------------------");
//		}
		
		
		
		if(webDto.getQuestions()==null) {
			return "redirect:/admin/questionaire-list";
		}
		
		//webDto.getQuestions().removeIf(QuestionObj::hasNullField);
		//webDto.getQuestions().forEach(question -> question.getAnswers().removeIf(AnswerObj::hasNullField));
		//System.out.println(webDto.getQuestions().getFirst().getAnswers().size());
		questionaireService.saveQuestionaireQuestion(webDto);
		
		return "redirect:/admin/questionaire-list";
	}
}
