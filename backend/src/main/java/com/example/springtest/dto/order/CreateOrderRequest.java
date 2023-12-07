package com.example.springtest.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Service {
        private String type;

        private int count;
    }

    private String branch;

    private String clientName;

    private List<Service> services;

}
