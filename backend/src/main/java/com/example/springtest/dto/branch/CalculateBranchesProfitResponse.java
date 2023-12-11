package com.example.springtest.dto.branch;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculateBranchesProfitResponse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Data {
        private String branchId;
        private String branchAddress;

        private float income;
        private float spending;

        private float profit;
    }

    private List<Data> data;

}
