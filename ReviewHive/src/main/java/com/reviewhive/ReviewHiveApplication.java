package com.reviewhive;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.reviewhive.common.constant.CommonConstant;
import com.reviewhive.model.dao.UserDao;
import com.reviewhive.model.dao.entity.UserEntity;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ReviewHiveApplication {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(ReviewHiveApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		
		String username = env.getProperty("admin.username");
		String password = env.getProperty("admin.password");
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		
		UserEntity user = new UserEntity();
		user.setRole(CommonConstant.ADMIN_ROLE);
		user.setUsername(username);
		user.setPassword(encoder.encode(password));
		user.setCreatedDate(now);
		user.setUpdateDate(now);
		user.setDeleteFlg(false);
		
		userDao.save(user);
	}

}
