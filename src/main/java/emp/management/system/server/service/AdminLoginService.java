package emp.management.system.server.service;

import emp.management.system.server.entity.AdminLoginEntity;

public interface AdminLoginService {

    AdminLoginEntity login(String username,
                           String pass);
}