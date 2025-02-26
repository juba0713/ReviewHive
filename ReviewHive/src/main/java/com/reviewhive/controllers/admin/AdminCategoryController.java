package com.reviewhive.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.constant.MessageConstant;
import com.reviewhive.model.dto.CategoryDto;
import com.reviewhive.model.services.CategoryService;

import jakarta.validation.Valid;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;

	/**
	 * Show Category List Screen
	 * @return String
	 */
	@GetMapping("/category-list")
	public String showCategoriesScreen(
			Model model,
			@ModelAttribute CategoryDto webDto) {
		
		return "admin/category-list";
	}
	
	/**
	 * Show Category Add Screen
	 * @return String
	 */
	@GetMapping("/category-add")
	public String showCategoryAddScreen(@ModelAttribute CategoryDto webDto) {
		
		return "admin/category-add";
	}
	
	/**
	 * Post Category Add Screen
	 * @param webDto
	 * @param ra
	 * @param result
	 * @return String
	 */
	@PostMapping("/category-add")
	public String postCategoryAddScreen(
			Model model,
			@Valid @ModelAttribute CategoryDto webDto,
			BindingResult result,
			RedirectAttributes ra) {
		
		if(result.hasErrors()) {
			model.addAttribute("categoryDto", webDto);
			return "admin/category-add";
		}
		
		categoryService.saveCategory(webDto);
		
		String successMessage = webDto.getCategoryName() + MessageConstant.CATEGORY_ADDED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/category-list";
	}
	
	/**
	 * Show Category Add Screen
	 * @return String
	 */
	@GetMapping("/category-edit")
	public String showCategoryEditScreen(			
			@ModelAttribute CategoryDto webDto) {
		
		webDto.setCategory(categoryService.getCategoryById(webDto).getCategory());
		
		return "admin/category-edit";
	}
	
	/**
	 * Post Category Edit Screen
	 * @param webDto
	 * @param ra
	 * @param result
	 * @return String
	 */
	@PostMapping("/category-edit")
	public String postCategoryEditScreen(
			Model model,
			@ModelAttribute @Valid CategoryDto webDto,
			RedirectAttributes ra,
			BindingResult result) {
		
		if(result.hasErrors()) {
			return "admin/category-edit";
		}
		
		webDto.setUpdateType(CommonConstant.UPDATE_ALL);
		
		categoryService.updateCategory(webDto);
		
		String successMessage = webDto.getCategoryName() + MessageConstant.CATEGORY_EDITED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/category-list";
	}
	
	/**
	 * Delete Category
	 * @param webDto
	 * @param ra
	 * @return
	 */
	@PostMapping("/category-delete")
	public String postCategoryDeleteScreen(
			@ModelAttribute CategoryDto webDto,
			RedirectAttributes ra) {
		
		webDto.setUpdateType(CommonConstant.UPDATE_DELETE);
		
		categoryService.updateCategory(webDto);
		
		String successMessage = MessageConstant.CATEGORY_DELETED;
		
		ra.addFlashAttribute("message", successMessage);
		
		return "redirect:/admin/category-list";
	}
}
