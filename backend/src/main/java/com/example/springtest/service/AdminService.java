package com.example.springtest.service;

import com.example.springtest.model.Admin;
import com.example.springtest.repository.AdminRepository;
import org.springframework.stereotype.Service;

import com.example.springtest.model.Client;
import com.example.springtest.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin addAdmin(Admin admin) {
        return adminRepository.addAdmin(admin.getFullName(), admin.getPassword(), admin.getEmail(), admin.getCreationDate(), admin.getEditDate(), admin.getRole(), admin.getPhone(), admin.getSchedule());
    }


    public List<Admin> getAllAdmins() {
        return (List<Admin>) adminRepository.getAllAdmins();
    }

    @GetMapping("/getByLogin/{login}")
    public Admin getAdminByLogin(@PathVariable("login") String login) {
        return adminRepository.findByLogin(login).orElse(null);
    }

    @GetMapping("/getById/{id}")
    public Admin getAdminById(@PathVariable("id") Long id) {
        return adminRepository.getById(id).orElse(null);
    }
}

