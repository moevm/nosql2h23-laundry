package com.example.springtest.dto.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Service {

        private String type;

        private int count;

    }


    private String clientId;
    private String clientName;

    private String status;

    private String branchId;
    private String branchAddress;

    private double price;

    private List<Service> services;

    private String creationDate;
    private String editDate;

}
