package com.example.springtest.dto.warehouse;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveProductsRequest {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {

        private String name;

        private int count;

    }

    private String warehouse;

    private List<Data> products;

}
