package com.example.springtest.controller;


import com.example.springtest.dto.order.*;
import com.example.springtest.model.Order;
import com.example.springtest.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @GetMapping("/api/order/get")
    public GetOrderResponse getOrder(@RequestParam("id") String id) {
        return orderService.findOrderById(id);
    }

    @PostMapping("/api/order/create")
    public void createOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
    }

    @PostMapping("/api/order/cancel")
    public void cancelOrder(@RequestBody CancelOrderRequest request) {
        orderService.cancelOrders(request);
    }

    @PostMapping("/api/order/approve")
    public void approveOrder(@RequestBody ApproveOrderRequest request) {
        orderService.approveOrders(request);
    }

    @PostMapping("/api/order/ready")
    public void readyOrder(@RequestBody GetReadyOrderRequest request) {
        orderService.prepareOrders(request);
    }

    @PostMapping("/api/order/complete")
    public void completeOrder(@RequestBody CompleteOrderRequest request) {
        orderService.completeOrders(request);
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
