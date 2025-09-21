package com.ems.employeeMS.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.employeeMS.backend.entity.AddEmpEntity;
import com.ems.employeeMS.backend.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	public AddEmpEntity addEmployee(AddEmpEntity emp) {
		return repository.save(emp);
	}
	
	public List<AddEmpEntity> getAllEmployee() {
		return repository.findAll();
	}
	
	public  List<String> getAllEmployeeIds() {
		return repository.findAll().stream().map(AddEmpEntity::getEmpId).toList();	
	}

	public AddEmpEntity getEmployeeById(String empId) {
		return repository.findById(empId).orElse(null);
	}
	
	public AddEmpEntity updateEmployeeById(AddEmpEntity emp) {
		return repository.findById(emp.getEmpId()).map(existingEmp -> {
			if(emp.getName() != null) existingEmp.setName(emp.getName());
			if(emp.getFathername() != null) existingEmp.setFathername(emp.getFathername());
			if(emp.getEmail() != null) existingEmp.setEmail(emp.getEmail());
            if(emp.getDob() != null) existingEmp.setDob(emp.getDob());
            if(emp.getAddress() != null) existingEmp.setAddress(emp.getAddress());
            if(emp.getHighestEdu() != null) existingEmp.setHighestEdu(emp.getHighestEdu());
            if(emp.getDesignation() != null) existingEmp.setDesignation(emp.getDesignation());
            if(emp.getSalary() != null) existingEmp.setSalary(emp.getSalary());
            return repository.save(existingEmp);
		})
				.orElseThrow(() -> new RuntimeException(
	                    "Employee not found with id: " + emp.getEmpId()));
	}
	
	public boolean removeEmployeeByID(String empId) {
		if(repository.existsById(empId)) {
			repository.deleteById(empId);
		}
		return false;
	}

}
