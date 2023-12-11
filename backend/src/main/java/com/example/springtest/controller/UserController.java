package com.example.springtest.controller;

import com.example.springtest.dto.user.GetUserResponse;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Client;
import com.example.springtest.model.Employee;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.service.ClientService;
import com.example.springtest.service.EmployeeService;
import com.example.springtest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final UserService userService;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    @GetMapping("/api/user/get")
    public GetUserResponse getUser (@RequestParam("id") String id) {
        UserRole userRole = userService.getUserRoleById(id);

        GetUserResponse response = new GetUserResponse();

        if (userRole == UserRole.CLIENT) {
            Client client = clientService.getClientById(id);

            response.setRole(client.getRole().toString());
            response.setName(client.getFullName());
            response.setEmail(client.getEmail());
            response.setCreationDate(client.getCreationDate().format(formatter));
            response.setEditDate(client.getEditDate().format(formatter));
        } else {
            Employee employee = employeeService.findEmployeeById(UUID.fromString(id));

            Branch branch = null;

            if (employee.getRole() == UserRole.ADMIN) {
                branch = employee.getBranchAdmin();
            } else if (employee.getRole() == UserRole.DIRECTOR) {
                branch = employee.getBranchDirector();
            }

            response.setName(employee.getFullName());
            response.setEmail(employee.getEmail());
            response.setPhone(employee.getPhone());
            response.setBranchId((branch == null) ? "" : branch.getId().toString());
            response.setBranchAddress((branch == null) ? "" : branch.getAddress());
            response.setWarehouseId((employee.getWarehouse() == null) ? "" : employee.getWarehouse().getId().toString());
            response.setWarehouseAddress((employee.getWarehouse() == null) ? "" : employee.getWarehouse().getAddress());
            response.setRole(employee.getRole().toString());
            response.setSchedule(employee.getSchedule());
            response.setCreationDate(employee.getCreationDate().format(formatter));
            response.setEditDate(employee.getEditDate().format(formatter));

        }

        return response;
    }

}
