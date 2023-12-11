package com.example.springtest.service;

import com.example.springtest.dto.order.*;
import com.example.springtest.exceptions.controller.NoSuchOrderException;
import com.example.springtest.model.Order;
import com.example.springtest.model.types.OrderState;
import com.example.springtest.model.types.ServiceType;
import com.example.springtest.repository.OrderRepository;
import com.example.springtest.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final static DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;

    final static DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;

    @Transactional
    public void createOrder(CreateOrderRequest request) {

        ZonedDateTime creationDateTime = ZonedDateTime.now();

        Map<ServiceType, Float> servicesPrice = serviceRepository.findAllServicesNoExtraData().stream().collect(
                Collectors.toMap(com.example.springtest.model.Service::getType, com.example.springtest.model.Service::getPrice)
        );

        float totalPrice = 0;

        for (CreateOrderRequest.Service serviceFromRequest : request.getServices()) {
            ServiceType type = ServiceType.valueOf(serviceFromRequest.getType());

            totalPrice += servicesPrice.get(type) * serviceFromRequest.getCount();
        }

        Order order = orderRepository.createOrder(
                UUID.randomUUID(),
                totalPrice,
                OrderState.NEW,
                request.getClientName(),
                request.getBranch(),
                creationDateTime,
                creationDateTime
        );

        for (CreateOrderRequest.Service serviceFromRequest : request.getServices()) {
            orderRepository.connectServiceWithOrder(order.getId(), ServiceType.valueOf(serviceFromRequest.getType()), serviceFromRequest.getCount());
        }
    }

    @Transactional
    public List<Order> getAllOrders(GetAllRequest request) {

        ZonedDateTime start, end;

        if (request.getDates().getStart() == null || request.getDates().getEnd() == null) {
            Instant minInstant = Instant.ofEpochMilli(Long.MIN_VALUE);
            start = minInstant.atZone(ZoneOffset.UTC);

            Instant maxInstant = Instant.ofEpochMilli(Long.MAX_VALUE);
            end = maxInstant.atZone(ZoneOffset.UTC);
        } else {
            start = ZonedDateTime.parse(request.getDates().getStart(), formatter);

            end = ZonedDateTime.parse(request.getDates().getEnd(), formatter);
            end = end.plusDays(1).minusSeconds(1);
        }

        List<OrderState> possibleStates = request.getStates().stream().map(OrderState::valueOf).toList();

        if (possibleStates.isEmpty()) {
            possibleStates = Arrays.stream(OrderState.values()).toList();
        }

        List<Order> orderList;

        if (!Objects.equals(request.getClientId(), "")) {
            orderList = orderRepository.findAllOrdersForClient(start, end, possibleStates, request.getBranch(), request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1), request.getClientId());

            orderList.removeIf((order) -> order.getClient() == null);
        } else {
            orderList = orderRepository.findAllOrders(start, end, possibleStates, request.getBranch(), request.getElementsOnPage(), request.getElementsOnPage() * (request.getPage() - 1));
        }

        return orderList;
    }

    @Transactional
    public GetOrderResponse findOrderById(String id) {
        Order order = orderRepository.findOrderById(UUID.fromString(id)).orElseThrow(NoSuchOrderException::new);

        return GetOrderResponse.builder()
                .clientId(order.getClient().getId().toString())
                .clientName(order.getClient().getFullName())
                .status(order.getState().toString())
                .branchId(order.getBranch().getId().toString())
                .branchAddress(order.getBranch().getAddress())
                .price(order.getPrice())
                .services(order.getServices().stream().map(contains -> GetOrderResponse.Service.builder()
                        .count(contains.getAmount())
                        .type(contains.getService().getType().toString())
                        .build()
                ).toList())
                .creationDate(order.getCreationDate().format(formatter1))
                .editDate(order.getEditDate().format(formatter1))
                .build();

    }

    @Transactional
    public long getTotalCount(GetTotalOrdersCountRequest request) {
        ZonedDateTime start, end;

        if (request.getDates().getStart() == null || request.getDates().getEnd() == null) {
            Instant minInstant = Instant.ofEpochMilli(Long.MIN_VALUE);
            start = minInstant.atZone(ZoneOffset.UTC);

            Instant maxInstant = Instant.ofEpochMilli(Long.MAX_VALUE);
            end = maxInstant.atZone(ZoneOffset.UTC);
        } else {
            start = ZonedDateTime.parse(request.getDates().getStart(), formatter);

            end = ZonedDateTime.parse(request.getDates().getEnd(), formatter);
            end = end.plusDays(1).minusSeconds(1);
        }

        List<OrderState> possibleStates = request.getStates().stream().map(OrderState::valueOf).toList();

        if (possibleStates.isEmpty()) {
            possibleStates = Arrays.stream(OrderState.values()).toList();
        }

        int count = 0;

        if (!Objects.equals(request.getClientId(), "")) {
            List<Order> orderList = orderRepository.getTotalCountForClient(start, end, possibleStates, request.getBranch(), request.getClientId());

            orderList.removeIf((order) -> order.getClient() == null);

            count = orderList.size();
        } else {
            count = orderRepository.getTotalCount(start, end, possibleStates, request.getBranch());
        }

        return (int) (Math.ceil(count / (double) request.getElementsOnPage()));
    }

    @Transactional
    public void approveOrders(ApproveOrderRequest request) {
        ZonedDateTime editDateTime = ZonedDateTime.now();

        orderRepository.setNewStateForOrders(request.getOrderIds().stream().map(UUID::fromString).toList(), OrderState.NEW, OrderState.ACTIVE, editDateTime);
    }

    @Transactional
    public void prepareOrders(GetReadyOrderRequest request) {
        ZonedDateTime editDateTime = ZonedDateTime.now();

        orderRepository.setNewStateForOrders(request.getOrderIds().stream().map(UUID::fromString).toList(), OrderState.ACTIVE, OrderState.READY, editDateTime);
    }

    @Transactional
    public void completeOrders(CompleteOrderRequest request) {
        ZonedDateTime editDateTime = ZonedDateTime.now();

        orderRepository.setNewStateForOrders(request.getOrderIds().stream().map(UUID::fromString).toList(), OrderState.READY, OrderState.COMPLETED, editDateTime);
    }

    @Transactional
    public void cancelOrders(CancelOrderRequest request) {
        ZonedDateTime editDateTime = ZonedDateTime.now();

        orderRepository.cancelOrders(request.getOrderIds().stream().map(UUID::fromString).toList(), editDateTime);
    }
}

