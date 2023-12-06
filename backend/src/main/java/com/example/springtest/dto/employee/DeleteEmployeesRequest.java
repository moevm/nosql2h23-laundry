package com.example.springtest.dto.employee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteEmployeesRequest {

    private List<String> idList;

}
