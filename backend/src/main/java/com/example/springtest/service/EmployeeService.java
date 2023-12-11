package com.example.springtest.service;

import com.example.springtest.dto.employee.*;
import com.example.springtest.exceptions.controller.NoSuchUserException;
import com.example.springtest.exceptions.controller.UserAlreadyExistsException;
import com.example.springtest.model.Employee;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.repository.EmployeeRepository;
import com.example.springtest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Employee findEmployeeById(UUID id) {
        return employeeRepository.findEmployeeById(id).orElseThrow(NoSuchUserException::new);
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
    public void createEmployee(CreateEmployeeCustomRequest request) {
        Optional<User> userOptional = userRepository.findByLogin(request.getLogin());
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String roleString = request.getRole();

        // TODO: Catch IllegalArgumentException?
        UserRole role = UserRole.valueOf(roleString);

        ZonedDateTime creationDateTime = ZonedDateTime.now();

        employeeRepository.createEmployee(
                request.getId(),
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

    @Transactional
    public CalculateSalariesResponse calculateSalaries(CalculateSalariesRequest request) {

        Map<UserRole, Float> salaryToRole = new HashMap<>();
        salaryToRole.put(UserRole.ADMIN, 34f);
        salaryToRole.put(UserRole.DIRECTOR, 68f);
        salaryToRole.put(UserRole.SUPERUSER, 500f);

        LocalDate startDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.RFC_1123_DATE_TIME);
        LocalDate endDate = LocalDate.parse(request.getEndDate(), DateTimeFormatter.RFC_1123_DATE_TIME);

        Set<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        long workDaysBetween = startDate.datesUntil(endDate)
                .filter(d -> !weekend.contains(d.getDayOfWeek()))
                .count();

        List<Employee> employeeList = employeeRepository.findAllEmployees(request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));

        List<CalculateSalariesResponse.Data> dataList = new ArrayList<>();

        for (Employee employee : employeeList) {

            CalculateSalariesResponse.Data data = CalculateSalariesResponse.Data.builder()
                    .name(employee.getFullName())
                    .role(employee.getRole().toString())
                    .maxSalary(salaryToRole.get(employee.getRole()))
                    .daysAtWork(employee.getShifts().stream()
                            .filter((shift -> (shift.getDate().isAfter(startDate) || shift.getDate().isEqual(startDate)) && (shift.getDate().isBefore(endDate) || shift.getDate().isEqual(endDate))))
                            .toList().size()
                    )
                    .build();

            data.setResultSalary(data.getMaxSalary() / workDaysBetween * data.getDaysAtWork());

            dataList.add(data);
        }

        return new CalculateSalariesResponse(dataList, workDaysBetween);
    }
}
