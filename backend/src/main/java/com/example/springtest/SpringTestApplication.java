package com.example.springtest;

import com.example.springtest.dto.branch.CreateBranchRequest;
import com.example.springtest.dto.client.CreateClientRequest;
import com.example.springtest.dto.employee.CreateEmployeeRequest;
import com.example.springtest.model.Branch;
import com.example.springtest.model.Employee;
import com.example.springtest.model.types.ServiceType;
import com.example.springtest.model.types.UserRole;
import com.example.springtest.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }

}
