package com.ems.employeeMS.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.employeeMS.backend.entity.AdminLoginEntity;
import com.ems.employeeMS.backend.service.AdminLoginService;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {
	
	@Autowired
	public AdminLoginService adminService;
	
	@PostMapping("/login")
	public AdminLoginEntity login(@RequestBody AdminLoginEntity admin ) {
		return adminService.login(admin.getUsername(), admin.getPassword());
	}

}
