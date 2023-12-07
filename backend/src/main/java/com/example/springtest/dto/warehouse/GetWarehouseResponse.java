package com.example.springtest.dto.warehouse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWarehouseResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ProductData {

        private String type;

        private int amount;

    }

    private String address;

    private String branchId;
    private String branchAddress;

    private List<String> schedule;

    private List<ProductData> products;

    private String creationDate;
    private String editDate;
}
