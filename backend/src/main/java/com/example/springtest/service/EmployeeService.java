package com.example.springtest.service;

import com.example.springtest.dto.employeeService.NewEmployeeData;
import com.example.springtest.model.Employee;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    @Transactional
    public Employee addEmployee(NewEmployeeData data) {
        LocalDateTime localDateTime = LocalDateTime.now();

        Employee employee = Employee.employeeBuilder()
                .id(UUID.randomUUID())
                .login(data.getLogin())
                .role(UserRole.valueOf(data.getRole()))
                .password(data.getPassword())
                .fullName(data.getFullName())
                .email(data.getEmail())
                .phone(data.getPhone())
                .schedule(data.getSchedule())
                .creationDate(localDateTime)
                .editDate(localDateTime)
                .build();

        return repository.addEmployee(
                employee.getId(),
                employee.getRole(),
                employee.getLogin(),
                employee.getPassword(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getSchedule(), // TODO: may be format is wrong!
                employee.getCreationDate(),
                employee.getEditDate()
        );
    }

    @Transactional
    public Optional<Employee> findEmployeeByLogin(String login) {
        return repository.findByLogin(login);
    }
}
