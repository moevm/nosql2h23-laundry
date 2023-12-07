package com.example.springtest.controller;


import com.example.springtest.dto.order.CreateOrderRequest;
import com.example.springtest.dto.order.GetAllRequest;
import com.example.springtest.dto.order.GetAllResponse;
import com.example.springtest.dto.order.GetTotalOrdersCountRequest;
import com.example.springtest.model.Order;
import com.example.springtest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
// TODO: check if works
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/order/create")
    public void createOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
    }

    @PostMapping("/api/order/all")
    public GetAllResponse getAllOrders(@RequestBody GetAllRequest request) {
        List<Order> employeeList = orderService.getAllOrders(request);

        List<GetAllResponse.Data> data = employeeList.stream()
                .map(client -> GetAllResponse.Data.builder()
                        .id(client.getId().toString())
                        .date(client.getCreationDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                        .status(client.getState().toString())
                        .branch(client.getBranch().getAddress())
                        .build()
                ).toList();

        return new GetAllResponse(data);
    }

    @PostMapping("/api/order/all_count")
    public long getTotalOrdersCount(@RequestBody GetTotalOrdersCountRequest request) {
        return orderService.getTotalCount(request);
    }

}
