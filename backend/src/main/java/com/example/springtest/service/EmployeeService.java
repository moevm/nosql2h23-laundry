package com.example.springtest.service;

import com.example.springtest.dto.employee.CreateEmployeeRequest;
import com.example.springtest.dto.employee.GetAllRequest;
import com.example.springtest.dto.employee.GetTotalEmployeesCountRequest;
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
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void createEmployee(CreateEmployeeRequest request) {
        Optional<User> userOptional = userRepository.findByLogin(request.getLogin());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String roleString = request.getRole();

        // TODO: Catch IllegalArgumentException?
        UserRole role = UserRole.valueOf(roleString);

        ZonedDateTime creationDateTime = ZonedDateTime.now();

        employeeRepository.createEmployee(
                UUID.randomUUID(),
                role,
                request.getLogin(),
                request.getPassword(),
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getSchedule(),
                creationDateTime,
                creationDateTime
        );
    }

    @Transactional
    public List<Employee> getAllEmployees(GetAllRequest request) {

        List<UserRole> possibleRoles = request.getRoles().stream().map(UserRole::valueOf).toList();

        if (possibleRoles.isEmpty()) {
            possibleRoles = Arrays.stream(UserRole.values()).toList();
        }

        return employeeRepository.getAllEmployees(request.getName(), request.getPhone(), possibleRoles, request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));
    }

    @Transactional
    public long getTotalCount(GetTotalEmployeesCountRequest request) {
        List<UserRole> possibleRoles = request.getRoles().stream().map(UserRole::valueOf).toList();

        if (possibleRoles.isEmpty()) {
            possibleRoles = Arrays.stream(UserRole.values()).toList();
        }

        int count = employeeRepository.getTotalCount(request.getName(), request.getPhone(), possibleRoles);

        return (int) (Math.ceil(count / (double) request.getElementsOnPage()));
    }

    @Transactional
    public void deleteEmployees(List<UUID> idList) {
        employeeRepository.deleteEmployees(idList);
    }
}
