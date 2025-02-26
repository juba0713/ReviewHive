package com.reviewhive.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.reviewhive.model.dao.entity.UserEntity;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Service
@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {

}
