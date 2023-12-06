package com.example.springtest.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetTotalWarehousesCountRequest {

    private String address;

    private String branch;

    private int elementsOnPage;

}
