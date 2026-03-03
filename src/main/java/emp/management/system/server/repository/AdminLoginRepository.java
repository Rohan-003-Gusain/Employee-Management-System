package emp.management.system.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import emp.management.system.server.entity.AdminLoginEntity;

public interface AdminLoginRepository extends JpaRepository<AdminLoginEntity, String>{

	AdminLoginEntity findByUsernameAndPassword(String username, String password); 
}
