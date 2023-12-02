package com.example.springtest.controller;

import com.example.springtest.model.Admin;
import com.example.springtest.model.Client;
import com.example.springtest.service.AdminService;
import com.example.springtest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add")
    public @ResponseBody Admin addAdmin(@RequestBody Admin admin) {
        return adminService.addAdmin(admin);
    }

    @GetMapping("/getAll")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/findByLogin/{login}")
    public Admin getAdminByLogin(@PathVariable("login") String login) {
        return adminService.getAdminByLogin(login);
    }

    @GetMapping("/findById/{id}")
    public Admin getAdminById(@PathVariable("id") Long id) {
        return adminService.getAdminById(id);
    }
}
