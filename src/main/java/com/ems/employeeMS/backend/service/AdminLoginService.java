package com.ems.employeeMS.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.employeeMS.backend.entity.AdminLoginEntity;
import com.ems.employeeMS.backend.repository.AdminLoginRepository;

@Service
public class AdminLoginService {

	@Autowired
	private AdminLoginRepository adminRepo;
	
	public AdminLoginEntity login(String username, String password) {
		AdminLoginEntity admin = adminRepo.findByUsernameAndPassword(username, password);
		if(admin != null) {
			return admin;
		}
		return null;
	}
	
}
