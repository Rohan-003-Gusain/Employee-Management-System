package emp.management.system.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emp.management.system.server.entity.AddEmpEntity;
import emp.management.system.server.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }
	
	@PostMapping("/add")
	public AddEmpEntity addEmployee(@RequestBody AddEmpEntity emp) {
		return service.addEmployee(emp);
	}
	
	@GetMapping("/all")
	public List<AddEmpEntity> getAllEmployee() {
		return service.getAllEmployees();
	}
	
	@GetMapping("/ids")
	public  List<String> getAllEmployeeIds() {
		return service.getAllEmployeeIds();	
	}

	@GetMapping("/get/{empId}")
	public ResponseEntity<AddEmpEntity> getEmployeeById(@PathVariable String empId) {
		AddEmpEntity emp =
                service.getEmployeeById(empId);

        if (emp == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(emp);
	}
	
	@PutMapping("/update")
	public ResponseEntity<AddEmpEntity> updateEmployee(@RequestBody AddEmpEntity emp) {
		AddEmpEntity updated =
                service.updateEmployeeById(emp);

        if (updated == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<Void> removeEmployeeByID(@PathVariable String empId) {
		boolean removed = service.removeEmployeeById(empId);
		
		if (!removed)
            return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
	}
	
}