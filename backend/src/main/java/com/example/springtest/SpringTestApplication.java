package com.example.springtest;

import com.example.springtest.dto.clientService.NewClientData;
import com.example.springtest.dto.employeeService.NewEmployeeData;
import com.example.springtest.model.Employee;
import com.example.springtest.model.User;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.service.ClientService;
import com.example.springtest.service.EmployeeService;
import com.example.springtest.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringTestApplication.class, args);

        ClientService clientService = context.getBean(ClientService.class);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        UserService userService = context.getBean(UserService.class);

        try {
            clientService.addClient(NewClientData.builder()
                    .login("Client")
                    .password("Password")
                    .fullName("Клиент")
                    .email("client@client.com")
                    .build());
        } catch (Exception ignored) {
        }

        try {
            employeeService.addEmployee(NewEmployeeData.builder()
                    .login("Admin")
                    .password("Password")
                    .role(UserRole.ADMIN.toString())
                    .fullName("Администратор")
                    .email("admin@admin.ru")
                    .phone("+73456754324356")
                    .schedule(List.of("Monday", "12", "15", "Tuesday", "10", "19"))
                    .build());
        } catch (Exception ignored) {
        }

        try {
            employeeService.addEmployee(NewEmployeeData.builder()
                    .login("Director")
                    .password("Password")
                    .role(UserRole.DIRECTOR.toString())
                    .fullName("Директор Филиала")
                    .email("director@director.ru")
                    .phone("+435682475")
                    .schedule(List.of("Monday", "12", "15", "Friday", "10", "19"))
                    .build());
        } catch (Exception ignored) {
        }

        try {
            employeeService.addEmployee(NewEmployeeData.builder()
                    .login("Superuser")
                    .password("Password")
                    .role(UserRole.SUPERUSER.toString())
                    .fullName("Суперпользователь")
                    .email("super@user.us")
                    .phone("+3345765638")
                    .schedule(List.of("Monday", "12", "15", "Tuesday", "10", "19"))
                    .build());
        } catch (Exception ignored) {
        }

    }

}
