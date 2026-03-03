package emp.management.system.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emp.management.system.server.entity.AdminLoginEntity;
import emp.management.system.server.service.AdminLoginService;

@RestController
@RequestMapping("/api/admin")
public class AdminLogin {
	
	private final AdminLoginService service;
	
	public AdminLogin(AdminLoginService service) {
        this.service = service;
    }
	
	@PostMapping("/login")
	public AdminLoginEntity login(@RequestBody AdminLoginEntity admin ) {
		return service.login(admin.getUsername(), admin.getPassword());
	}

}