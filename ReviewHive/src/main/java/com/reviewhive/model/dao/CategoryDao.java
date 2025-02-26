package com.reviewhive.model.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewhive.model.dao.entity.CategoryDetailsEntity;
import com.reviewhive.model.dao.entity.CategoryEntity;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Service
@Repository
@Transactional
public interface CategoryDao extends JpaRepository<CategoryEntity, Integer>{
	
	/*
	 * Query for Getting All Query
	 */
	public static final String GET_ALL_CATEGORY_BY_PAGE = "SELECT e.id, "
			+ "e.category_name, "
			+ "e.description, "
			+ "e.is_open, "
			+ "e.hex_color, "
			+ "e.created_date, "
			+ "e.updated_date, "
			+ "CAST(CEIL(COUNT(*) OVER () / :limit) AS INTEGER) AS total_page "
			+ "FROM m_category e "
			+ "WHERE e.delete_flg = false "
			+ "ORDER BY e.id ASC "
			+ "LIMIT :limit OFFSET :offset";	
	/**
	 * Get All Category Raw
	 * @return List<Object[]>
	 * @throws DataAccessException
	 */
	@Query(value=GET_ALL_CATEGORY_BY_PAGE, nativeQuery=true)
	public List<Object[]> getAllCategoryByPageRaw(
			@Param("limit") double limit,
			@Param("offset") int offset) throws DataAccessException;
	
	/**
	 * Get All Category
	 * @param limit
	 * @param offset
	 * @return List<CategoryDetailsEntity>
	 */
	default List<CategoryDetailsEntity> getAllCategoryByPage(int limit, int offset){
		List<Object[]> rawResults = getAllCategoryByPageRaw(limit, offset);
	    List<CategoryDetailsEntity> categories = new ArrayList<>();

	    for (Object[] objects : rawResults) {
	    	CategoryDetailsEntity category = new CategoryDetailsEntity(objects);  
	    	categories.add(category);
	    }

	    return categories;
	}
	
	/*
	 * Query for updating category status
	 */
	public static final String UPDATE_CATEGORY_STATUS = "UPDATE m_category "
			+ "SET is_open = :status "
			+ "WHERE id = :id ";
	
	/**
	 * Update Category Status
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_CATEGORY_STATUS, nativeQuery=true)
	public void updateCategoryStatus(
			@Param("status") boolean status, 
			@Param("id") int id);
	
	/*
	 * Query for getting a single category by its id
	 */
	public static final String GET_CATEGORY_BY_ID = "SELECT e "
			+ "FROM CategoryEntity e "
			+ "WHERE e.id = :id "
			+ "AND e.deleteFlg = false ";
	
	/**
	 * Get Category by its id
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	@Query(value=GET_CATEGORY_BY_ID)
	public CategoryEntity getCategoryById(int id) throws DataAccessException;
	
	/*
	 * Query for updating category all
	 */
	public static final String UPDATE_CATEGORY = "UPDATE m_category "
			+ "SET category_name = :categoryName, "
			+ "description = :categoryDescription, "
			+ "hex_color = :categoryColor, "
			+ "is_open = :categoryStatus, "
			+ "updated_date = :updateDate "
			+ "WHERE id = :id ";
	
	/**
	 * Update Category all
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_CATEGORY, nativeQuery=true)
	public void updateCategoryAll(
			@Param("categoryName") String categoryName, 
			@Param("categoryDescription") String categoryDescription,
			@Param("categoryColor") String categoryColor,
			@Param("categoryStatus") boolean categoryStatus,
			@Param("updateDate") Timestamp updateDate,
			@Param("id") int id);
	
	/*
	 * Query for updating category delete
	 */
	public static final String UPDATE_CATEGORY_DELETE = "UPDATE m_category "
			+ "SET delete_flg = true "
			+ "WHERE id = :id ";
	
	/**
	 * Update Category Delete
	 * @param status
	 * @param id
	 */
	@Modifying
	@Query(value=UPDATE_CATEGORY_DELETE, nativeQuery=true)
	public void updateCategoryDelete(@Param("id") int id);
	
	/*
	 * Query for retrieving all category without paging
	 */
	public static final String GET_ALL_CATEGORY = "SELECT e "
			+ "FROM CategoryEntity e "
			+ "WHERE e.isOpen = true "
			+ "AND e.deleteFlg = false ";
	
	/**
	 * Get All Category
	 * @return List<CategoryEntity>
	 * @throws DataAccessException
	 */
	@Query(value=GET_ALL_CATEGORY)
	public List<CategoryEntity> getAllCategory() throws DataAccessException;
}
