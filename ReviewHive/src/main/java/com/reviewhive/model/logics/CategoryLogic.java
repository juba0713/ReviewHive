package com.reviewhive.model.logics;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Service
public interface CategoryLogic {

	/**
	 * Save Category
	 * @param inDto
	 */
	public void saveCategory(CategoryEntity entity);
	
	/**
	 * Get All Category
	 * @param page
	 */
	public List<CategoryDetailsEntity> getAllCategoryByPage(int page);
	
	/**
	 * Update Category Status
	 * @param status
	 */
	public void updateCategoryStatus(boolean status, int id);
	
	/**
	 * Update Category All Data
	 * @param categoryName
	 * @param categoryDescription
	 * @param categoryColor
	 * @param categoryStatus
	 * @param updatedDate
	 * @param id
	 */
	public void updateCategoryAll(String categoryName,
			String categoryDescription,
			String categoryColor,
			boolean categoryStatus,
			Timestamp updatedDate, 
			int id);
	
	/**
	 * Update Category Delete
	 * @param id
	 */
	public void updateCategoryDelete(int id);
	
	/**
	 * Get Category by its id
	 * @param id
	 * @return
	 */
	public CategoryEntity getCategoryById(int id);
	
	/**
	 * Get All Category
	 * @return List<CategoryEntity>
	 */
	public List<CategoryEntity> getAllCategory();
}