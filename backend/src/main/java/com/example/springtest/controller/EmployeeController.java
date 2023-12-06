package com.example.springtest.controller;

import com.example.springtest.dto.employee.GetDirectorsWithoutBranchResponse;
import com.example.springtest.model.Employee;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("api/employee/get_directors_no_branch")
    public GetDirectorsWithoutBranchResponse getDirectorsWithoutBranch() {
        List<Employee> directors = employeeService.findEmployeeWithoutBranch(UserRole.DIRECTOR);

        return GetDirectorsWithoutBranchResponse.builder()
                .names(directors.stream().map(User::getFullName).toList())
                .build();
    }

    @GetMapping("api/employee/get_admins_no_branch")
    public GetDirectorsWithoutBranchResponse getAdminsWithoutBranch() {
        List<Employee> admins = employeeService.findEmployeeWithoutBranch(UserRole.ADMIN);

        return GetDirectorsWithoutBranchResponse.builder()
                .names(admins.stream().map(User::getFullName).toList())
                .build();
    }


}
