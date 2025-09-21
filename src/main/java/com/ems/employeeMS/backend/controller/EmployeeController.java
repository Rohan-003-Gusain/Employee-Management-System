package com.ems.employeeMS.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.employeeMS.backend.entity.AddEmpEntity;
import com.ems.employeeMS.backend.service.EmployeeService;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@PostMapping("/add")
	public AddEmpEntity addEmployee(@RequestBody AddEmpEntity emp) {
		return service.addEmployee(emp);
	}
	
	@GetMapping("/all")
	public List<AddEmpEntity> getAllEmployee() {
		return service.getAllEmployee();
	}
	
	@GetMapping("/ids")
	public  List<String> getAllEmployeeIds() {
		return service.getAllEmployeeIds();	
	}

	@GetMapping("/get/{empId}")
	public AddEmpEntity getEmployeeById(@PathVariable String empId) {
		return service.getEmployeeById(empId);
	}
	
	@PutMapping("/update")
	public AddEmpEntity updateEmployee(@RequestBody AddEmpEntity emp) {
		return service.updateEmployeeById(emp);
		
	}
	
	@DeleteMapping("/delete/{empId}")
	public boolean removeEmployeeByID(@PathVariable String empId) {
		return service.removeEmployeeByID(empId);
	}
	
}
