package com.example.springtest.dto.branch;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculateBranchesLoadResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String branchId;
        private String branchAddress;

        private float spentProductsCoefficient;
        private int servicesCompleted;
    }

    private List<Data> data;

}
