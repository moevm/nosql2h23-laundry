package com.example.springtest.service;

import com.example.springtest.model.Order;
import com.example.springtest.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order addOrder(Order order) {
        return orderRepository.addOrderCustomQuery(order.getStatus(), order.getPrice(), order.getCreationDate(), order.getEditDate());
    }

    // Метод для получения всех пользователей
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.getAllOrdersCustomQuery();
    }
}

