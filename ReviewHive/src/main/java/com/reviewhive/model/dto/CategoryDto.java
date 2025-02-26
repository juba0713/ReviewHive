package com.reviewhive.model.dto;

import java.util.List;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.common.constant.MessageConstant;
import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@Data
public class CategoryDto {
	
	private int id;
	
	@NotBlank(message= MessageConstant.CATEGORY_NAME_REQUIRED)
	public String categoryName;
	
	@NotBlank(message= MessageConstant.CATEGORY_NAME_REQUIRED)
	public String categoryColor;
	
	@NotBlank(message= MessageConstant.CATEGORY_STATUS_REQUIRED)
	private String categoryStatus;
	
	@NotBlank(message= MessageConstant.CATEGORY_DESCRIPTION_REQUIRED)
	private String categoryDescription;
	
	private CategoryEntity category;
	
	private List<CategoryDetailsEntity> categories;
	
	private List<CategoryEntity> categoriesSelection;
	
	private int page;
	
	private String updateType;
}
