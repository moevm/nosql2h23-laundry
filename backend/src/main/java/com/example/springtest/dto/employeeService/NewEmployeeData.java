package com.example.springtest.dto.employeeService;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEmployeeData {

    private String role;

    private String login;

    private String password;

    private String fullName;

    private String email;

    private String phone;

    private List<String> schedule;

}
