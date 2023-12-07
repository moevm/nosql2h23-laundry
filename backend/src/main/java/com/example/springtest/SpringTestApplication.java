package com.example.springtest;

import com.example.springtest.dto.branch.CreateBranchRequest;
import com.example.springtest.dto.branch.GetAllRequest;
import com.example.springtest.dto.client.CreateClientRequest;
import com.example.springtest.dto.employee.CreateEmployeeRequest;
import com.example.springtest.dto.employeeService.NewEmployeeData;
import com.example.springtest.dto.order.CreateOrderRequest;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Employee;
import com.example.springtest.model.types.ServiceType;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringTestApplication.class, args);

        ClientService clientService = context.getBean(ClientService.class);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        try {
            clientService.createClient(CreateClientRequest.builder()
                    .login("Client")
                    .password("Password")
                    .name("Клиент")
                    .email("client@client.com")
                    .build());
        } catch (Exception ignored) {
        }

        try {
            employeeService.createEmployee(CreateEmployeeRequest.builder()
                    .login("Admin")
                    .password("Password")
                    .role(UserRole.ADMIN.toString())
                    .name("Администратор")
                    .email("admin@admin.ru")
                    .phone("+73456754324356")
                    .schedule(List.of("Monday", "12", "15", "Tuesday", "10", "19"))
                    .build());
        } catch (Exception ignored) {
        }

        try {
            employeeService.createEmployee(CreateEmployeeRequest.builder()
                    .login("Director")
                    .password("Password")
                    .role(UserRole.DIRECTOR.toString())
                    .name("Директор Филиала")
                    .email("director@director.ru")
                    .phone("+435682475")
                    .schedule(List.of("Monday", "12", "15", "Friday", "10", "19"))
                    .build());
        } catch (Exception ignored) {
        }

        try {
            employeeService.createEmployee(CreateEmployeeRequest.builder()
                    .login("Superuser")
                    .password("Password")
                    .role(UserRole.SUPERUSER.toString())
                    .name("Суперпользователь")
                    .email("super@user.us")
                    .phone("+3345765638")
                    .schedule(List.of("Monday", "12", "15", "Tuesday", "10", "19"))
                    .build());
        } catch (Exception ignored) {
        }

        BranchService branchService = context.getBean(BranchService.class);

        try {
            Employee admin = employeeService.findEmployeeByLogin("Admin").get();
            Employee director = employeeService.findEmployeeByLogin("Director").get();

            Branch branch = branchService.createBranch(CreateBranchRequest.builder()
                    .address("Address")
                    .directorName(director.getFullName())
                    .adminName(admin.getFullName())
                    .warehouseAddress("")
                    .build());

        } catch (Exception pop) {
        }


        ServiceService serviceService = context.getBean(ServiceService.class);

        try {
            serviceService.createService(ServiceType.WASHING, 200);
        } catch (Exception ignored) {
        }

        try {
            serviceService.createService(ServiceType.DRYING, 300);
        } catch (Exception ignored) {
        }

        try {
            serviceService.createService(ServiceType.IRONING, 200);
        } catch (Exception ignored) {
        }

        try {
            serviceService.createService(ServiceType.POWDER, 50);
        } catch (Exception ignored) {
        }

        try {
            serviceService.createService(ServiceType.BLEACH, 100);
        } catch (Exception ignored) {
        }


        /*OrderService orderService = context.getBean(OrderService.class);

        orderService.createOrder(CreateOrderRequest.builder()
                .branch("Address")
                .clientName("Клиент")
                .services(List.of(
                        CreateOrderRequest.Service.builder()
                                .type(ServiceType.BLEACH.toString())
                                .count(1).build()
                ))
                .build());*/


    }

}