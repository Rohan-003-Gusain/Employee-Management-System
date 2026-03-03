package emp.management.system.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import emp.management.system.server.entity.AddEmpEntity;

public interface EmployeeRepository extends JpaRepository<AddEmpEntity, String>{

}
