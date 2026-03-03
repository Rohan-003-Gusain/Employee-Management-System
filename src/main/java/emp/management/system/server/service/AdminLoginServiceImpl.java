package emp.management.system.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import emp.management.system.server.entity.AdminLoginEntity;
import emp.management.system.server.repository.AdminLoginRepository;

@Service
public class AdminLoginServiceImpl
        implements AdminLoginService {

    @Autowired
    private AdminLoginRepository repo;

    @Override
    public AdminLoginEntity login(String username,
                                  String password) {
        return repo
            .findByUsernameAndPassword(username, password);
    }
}