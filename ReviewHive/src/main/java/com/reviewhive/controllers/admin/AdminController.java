package com.reviewhive.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Julius P. Basas
 * @added 12/17/2024
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/login")
	public String showAdminScreen() {
		
		return "login";
	}
	
	@GetMapping("/home")
	public String showHomeScreen() {
		
		return "admin/home";
	}
}
