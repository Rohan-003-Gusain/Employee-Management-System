package emp.management.system.server.service;

import java.util.List;

import emp.management.system.server.entity.AddEmpEntity;

public interface EmployeeService {

    AddEmpEntity addEmployee(AddEmpEntity emp);

    List<AddEmpEntity> getAllEmployees();

    AddEmpEntity getEmployeeById(String empId);
    
    List<String> getAllEmployeeIds();

    boolean removeEmployeeById(String empId);
    
    AddEmpEntity updateEmployeeById(AddEmpEntity emp);
	
}