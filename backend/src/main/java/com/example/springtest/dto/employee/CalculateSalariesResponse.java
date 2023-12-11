package com.example.springtest.dto.employee;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateSalariesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Data {
        private String name;

        private String role;

        private float maxSalary;

        private int daysAtWork;

        private float resultSalary;
    }

    private List<Data> employees;
    private long workingDays;

}
