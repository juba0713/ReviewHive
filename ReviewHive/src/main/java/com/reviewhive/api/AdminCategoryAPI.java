package com.reviewhive.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.model.dto.CategoryDto;
import com.reviewhive.model.services.CategoryService;

/**
 * @author Julius P. Basas
 * @added 12/18/2024
 */
@RestController
@RequestMapping("/admin/api/category")
public class AdminCategoryAPI {
	
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * Get All Category
	 * @param page
	 * @return
	 */
	@GetMapping("/get-all")
	public ResponseEntity<CategoryDto> getAllCategory(@RequestParam("page") int page){
		
		CategoryDto inDto = new CategoryDto();
		
		inDto.setPage(page);
		
		CategoryDto outDto = categoryService.getAllCategoryByPage(inDto);
		
		return ResponseEntity.ok(outDto);
	}
	
	/**
	 * Update Category Status
	 * @param page
	 * @return
	 */
	@PostMapping("/update-status")
	public ResponseEntity<Void> updateCategoryStatus(
			@RequestParam("id") int id,
			@RequestParam("status") String status){
		
		CategoryDto inDto = new CategoryDto();
		
		inDto.setCategoryStatus(status);
		
		inDto.setId(id);
		
		inDto.setUpdateType(CommonConstant.UPDATE_STATUS);
		
		categoryService.updateCategory(inDto);
		
		return ResponseEntity.ok().build();
	}
}
