package com.example.springtest.dto.employee;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateSalariesRequest {

    private String startDate;
    private String endDate;

    private int page;
    private int elementsOnPage;

    // Filters?
}
