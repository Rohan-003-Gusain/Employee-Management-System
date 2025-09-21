package com.ems.employeeMS.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ems.employeeMS.backend.entity.AdminLoginEntity;

public interface AdminLoginRepository extends JpaRepository<AdminLoginEntity, String>{

	AdminLoginEntity findByUsernameAndPassword(String username, String password); 
}
