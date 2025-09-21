package com.ems.employeeMS.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.employeeMS.backend.entity.AddEmpEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<AddEmpEntity, String>{

}
