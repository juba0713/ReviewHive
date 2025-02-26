package com.reviewhive.model.services;

import org.springframework.stereotype.Service;

import com.reviewhive.model.dto.CategoryDto;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Service
public interface CategoryService {
	
	/**
	 * Save Category
	 * @param inDto
	 */
	public void saveCategory(CategoryDto inDto);
	
	/**
	 * Get All Category
	 * @param inDto
	 * @return CategoryDto
	 */
	public CategoryDto getAllCategoryByPage(CategoryDto inDto);
	
	/**
	 * Update Category 
	 * @param inDto
	 */
	public void updateCategory(CategoryDto inDto);
	
	/**
	 * Get Category by its id
	 * @param inDto
	 * @return CategoryDto
	 */
	public CategoryDto getCategoryById(CategoryDto inDto);
	
	/**
	 * Get All Category
	 * @return CategoryDto
	 */
	public CategoryDto getAllCategory();
	
}
