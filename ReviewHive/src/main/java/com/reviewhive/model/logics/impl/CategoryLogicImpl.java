package com.reviewhive.model.logics.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reviewhive.common.util.ApplicationPropertiesRead;
import com.reviewhive.model.dao.CategoryDao;
import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;
import com.reviewhive.model.logics.CategoryLogic;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Service
public class CategoryLogicImpl implements CategoryLogic{
	
	@Autowired
	private CategoryDao categoryDao;

	/**
	 * Save Category
	 * @param inDto
	 */
	@Override
	public void saveCategory(CategoryEntity entity) {
		
		categoryDao.save(entity);
	}
	
	/**
	 * Get All Category
	 * @param page
	 */
	@Override
	public List<CategoryDetailsEntity> getAllCategoryByPage(int page) {
		
		int limit = Integer.valueOf(ApplicationPropertiesRead.getProperty("category.limit"));
		
		int offset = (page-1)*Integer.valueOf(limit);
		
		return categoryDao.getAllCategoryByPage(limit, offset);
	}

	/**
	 * Update Category Status
	 * @param status
	 */
	@Override
	public void updateCategoryStatus(boolean status, int id) {

		categoryDao.updateCategoryStatus(status, id);
	}

	/**
	 * Get Category by its id
	 * @param id
	 * @return
	 */
	@Override
	public CategoryEntity getCategoryById(int id) {
		
		return categoryDao.getCategoryById(id);
	}

	/**
	 * Update Category All Data
	 * @param categoryName
	 * @param categoryDescription
	 * @param categoryColor
	 * @param categoryStatus
	 * @param updatedDate
	 * @param id
	 */
	@Override
	public void updateCategoryAll(String categoryName, String categoryDescription, String categoryColor,
			boolean categoryStatus, Timestamp updatedDate, int id) {
		
		categoryDao.updateCategoryAll(categoryName, 
				categoryDescription, 
				categoryColor, 
				categoryStatus, 
				updatedDate,
				id);
	}

	/**
	 * Update Category Delete
	 * @param id
	 */
	@Override
	public void updateCategoryDelete(int id) {
		
		categoryDao.updateCategoryDelete(id);
	}

	/**
	 * Get All Category
	 * @return List<CategoryEntity>
	 */
	@Override
	public List<CategoryEntity> getAllCategory() {
		
		return categoryDao.getAllCategory();
	}

}
