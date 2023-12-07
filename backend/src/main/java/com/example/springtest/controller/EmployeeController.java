package com.example.springtest.controller;


import com.example.springtest.dto.employee.*;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Employee;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/api/employee/get")
    public GetEmployeeResponse getEmployee(@RequestParam("id") String id) {
        Employee employee = employeeService.findEmployeeById(UUID.fromString(id));

        Branch branch = null;

        if (employee.getRole() == UserRole.ADMIN) {
            branch = employee.getBranchAdmin();
        } else if (employee.getRole() == UserRole.DIRECTOR) {
            branch = employee.getBranchDirector();
        }

        return GetEmployeeResponse.builder()
                .name(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .branchId((branch == null) ? "" : branch.getId().toString())
                .branchAddress((branch == null) ? "" : branch.getAddress())
                .warehouseId((employee.getWarehouse() == null) ? "" : employee.getWarehouse().getId().toString())
                .warehouseAddress((employee.getWarehouse() == null) ? "" : employee.getWarehouse().getAddress())
                .role(employee.getRole().toString())
                .schedule(employee.getSchedule())
                .creationDate(employee.getCreationDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .editDate(employee.getEditDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build();
    }

    @PostMapping("/api/employee/create")
    public void createEmployee(@RequestBody CreateEmployeeRequest request) {
        employeeService.createEmployee(request);
    }

    @PostMapping("/api/employee/all")
    public GetAllResponse getAllEmployees(@RequestBody GetAllRequest request) {
        List<Employee> employeeList = employeeService.getAllEmployees(request);

        List<GetAllResponse.Data> data = employeeList.stream()
                .map(client -> GetAllResponse.Data.builder()
                        .id(client.getId().toString())
                        .name(client.getFullName())
                        .role(client.getRole().toString())
                        .phone(client.getPhone())
                        .build()
                ).toList();

        return new GetAllResponse(data);
    }

    @PostMapping("/api/employee/all_count")
    public long getTotalEmployeesCount(@RequestBody GetTotalEmployeesCountRequest request) {
        return employeeService.getTotalCount(request);
    }

    @PostMapping("/api/employee/calculate_salaries")
    public CalculateSalariesResponse getTotalEmployeesCount(@RequestBody CalculateSalariesRequest request) {
        return employeeService.calculateSalaries(request);
    }

    @PostMapping("/api/employee/delete_list")
    public void deleteEmployees(@RequestBody DeleteEmployeesRequest request) {
        employeeService.deleteEmployees(request.getIdList().stream().map(UUID::fromString).toList());
    }

    @GetMapping("api/employee/get_directors_no_branch")
    public GetDirectorsWithoutBranchResponse getDirectorsWithoutBranch() {
        List<Employee> directors = employeeService.findDirectorWithoutBranch();

        return GetDirectorsWithoutBranchResponse.builder()
                .names(directors.stream().map(User::getFullName).toList())
                .build();
    }

    @GetMapping("api/employee/get_admins_no_branch")
    public GetDirectorsWithoutBranchResponse getAdminsWithoutBranch() {
        List<Employee> admins = employeeService.findAdminWithoutBranch();

        return GetDirectorsWithoutBranchResponse.builder()
                .names(admins.stream().map(User::getFullName).toList())
                .build();
    }


}
