package com.example.springtest.service;

import com.example.springtest.dto.employeeService.NewEmployeeData;
import com.example.springtest.exceptions.controller.UserAlreadyExistsException;
import com.example.springtest.model.Employee;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.repository.EmployeeRepository;
import com.example.springtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Employee addEmployee(NewEmployeeData data) {

        Optional<User> userOptional = userRepository.findByLogin(data.getLogin());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }

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

        return employeeRepository.addEmployee(
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
        return employeeRepository.findByLogin(login);
    }

    @Transactional
    public List<Employee> findDirectorWithoutBranch() {
        return employeeRepository.findDirectorWithoutBranch();
    }

    @Transactional
    public List<Employee> findAdminWithoutBranch() {
        return employeeRepository.findAdminWithoutBranch();
    }
}
