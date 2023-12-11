package com.example.springtest.dto.employee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeResponse {

    private String name;

    private String email;

    private String phone;

    private String branchId;
    private String branchAddress;

    private String warehouseId;
    private String warehouseAddress;

    private String role;

    private List<String> schedule;

    private String creationDate;
    private String editDate;

}
