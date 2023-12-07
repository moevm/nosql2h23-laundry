package com.example.springtest.dto.branch;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculateBranchesProfitRequest {

    private String startDate;
    private String endDate;

    private int page;
    private int elementsOnPage;

}
