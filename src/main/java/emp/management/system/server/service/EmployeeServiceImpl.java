package emp.management.system.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emp.management.system.server.entity.AddEmpEntity;
import emp.management.system.server.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    @Override
    public AddEmpEntity addEmployee(AddEmpEntity emp) {
        return repo.save(emp);
    }

    @Override
    public List<AddEmpEntity> getAllEmployees() {
        return repo.findAll();
    }
    
    @Override
    public List<String> getAllEmployeeIds() {
    		return repo.findAll().stream().map(AddEmpEntity::getEmpId).toList();
    }

    @Override
    public AddEmpEntity getEmployeeById(String empId) {
        return repo.findById(empId).orElse(null);
    }

    @Override
    public boolean removeEmployeeById(String empId) {
        if (repo.existsById(empId)) {
        		repo.deleteById(empId);
        		return true;
        }
        return false;
    }
    
    @Override
    public AddEmpEntity updateEmployeeById(AddEmpEntity emp) {
    		if(repo.existsById(emp.getEmpId())){
            return repo.save(emp);
        }

        return null;
    }

}