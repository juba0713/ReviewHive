package com.reviewhive.model.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.util.DateFormatUtil;
import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;
import com.reviewhive.model.dto.CategoryDto;
import com.reviewhive.model.logics.CategoryLogic;
import com.reviewhive.model.services.CategoryService;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryLogic categoryLogic;

	/**
	 * Save Category
	 * @param inDto
	 */
	@Override
	public void saveCategory(CategoryDto inDto) {
		
		CategoryEntity category = new CategoryEntity();
		category.setCategoryName(inDto.getCategoryName());
		category.setDescription(inDto.getCategoryDescription());
		category.setHexColor(inDto.getCategoryColor());
		category.setIsOpen(inDto.getCategoryStatus().equals("true") ? true : false);
		category.setCreatedDate(DateFormatUtil.currentDate());
		category.setUpdatedDate(DateFormatUtil.currentDate());
		category.setDeleteFlg(false);
		
		//Save the category 
		categoryLogic.saveCategory(category);
		
	}

	/**
	 * Get All Category
	 * @param inDto
	 * @return CategoryDto
	 */
	@Override
	public CategoryDto getAllCategoryByPage(CategoryDto inDto) {
		
		CategoryDto outDto = new CategoryDto();
		
		List<CategoryDetailsEntity> categories = categoryLogic.getAllCategoryByPage(inDto.getPage());
		
		outDto.setCategories(categories);
		
		return outDto;
	}

	/**
	 * Update Category 
	 * @param inDto
	 */
	@Override
	public void updateCategory(CategoryDto inDto) {
		
		Boolean status = false;
		if(inDto.getUpdateType().equals(CommonConstant.UPDATE_STATUS) ||
			inDto.getUpdateType().equals(CommonConstant.UPDATE_ALL)){
			status = inDto.getCategoryStatus().equals("true") ? true : false;
		}
			
		switch(inDto.getUpdateType()) {
			
			case CommonConstant.UPDATE_STATUS:
				
				categoryLogic.updateCategoryStatus(status, inDto.getId());
				break;
			case CommonConstant.UPDATE_ALL:
				
				categoryLogic.updateCategoryAll(inDto.getCategoryName(), 
						inDto.getCategoryDescription(), 
						inDto.getCategoryColor(), 
						status, 
						DateFormatUtil.currentDate(), 
						inDto.getId());
				
				break;
			case CommonConstant.UPDATE_DELETE:
				
				categoryLogic.updateCategoryDelete(inDto.getId());
				
				break;
		}
		
	}

	/**
	 * Get Category by its id
	 * @param inDto
	 * @return CategoryDto
	 */
	@Override
	public CategoryDto getCategoryById(CategoryDto inDto) {
		
		CategoryDto outDto = new CategoryDto();
		
		CategoryEntity category = categoryLogic.getCategoryById(inDto.getId());
		
		outDto.setCategory(category);
		
		return outDto;
	}

	@Override
	public CategoryDto getAllCategory() {
		
		CategoryDto outDto = new CategoryDto();
		
		List<CategoryEntity> categoriesSelection = categoryLogic.getAllCategory();
		
		outDto.setCategoriesSelection(categoriesSelection);
		
		return outDto;
	}

}
