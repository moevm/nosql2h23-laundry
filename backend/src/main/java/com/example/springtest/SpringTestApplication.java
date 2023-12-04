package com.example.springtest;

import com.example.springtest.controller.ClientController;
import com.example.springtest.model.Client;
import com.example.springtest.service.ClientService;
import com.example.springtest.service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringTestApplication.class, args);

        ClientService clientService = context.getBean(ClientService.class);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        // TODO: add default client
//        clientService.addClient();

        // TODO: add default admin, manager and superuser
//        employeeService.addEmployee();

    }

}
